var playerId = null
var tables = null
var session = {
	tables : {}
}

function cometDInitialized() {
	console.log('cometDInitialized')
	$('#openingDiv').hide()
	$('#welcomeDiv').show()
}

function register() {
	callRegister($('#usernameInput').val(), afterRegistration)
}

function afterRegistration(aPlayerId) {
	playerId = aPlayerId
	subscribe('/leaderboard', leaderboardEventListener)
	subscribe('/player/'+playerId+'/query/response', queryResponseListener)
	publish('/query/request',tablesQuery())
	getBalance(displayBalance)
	console.log('afterRegistration', playerId)
}

//------------------------------listeners--------------------------
function tablePrivateEventListener(msg) {
	var event = JSON.parse(msg.data)
	var tableId = event.tableID.internal
	createTableIfMissing(tableId)
	console.log('tablePrivateEventListener', event)
	if (event.type == 'PlayerCardDealtEvent') {
		session.tables[tableId].cards.push(event.card)
		if (session.tables[tableId].cards.length == 2) {
			$('#playDiv').show()
		}
		displayNewCard(event)
	} 
	displaySession()
	displayTables()
}
function tablePublicEventListener(msg) {
	var event = JSON.parse(msg.data)
	console.log('tablePublicEventListener', event)
	if (event.type == 'PlayerSeatedEvent') {
		if (event.player.internal === playerId) {

		}
	} else if (event.type == 'PublicPlayerCardDealtEvent') {
		var tableId = event.tableID.internal
		createTableIfMissing(tableId)
		if (event.actingPlayer.internal != playerId) {
			session.tables[tableId].opponentCards = session.tables[tableId].opponentCards + 1
			console.log('opponents card', session.tables[tableId].opponentCards)
		} else {
			console.log('my card', event)
		}
		displayOpponentsCard(event)
	} else if (event.type == 'TableSeatingChangedEvent') {
		var tableId = event.id.internal
		createTableIfMissing(tableId)
		session.tables[tableId].players = event.players		
		displayTable(event)
	} else if (event.type == 'GameStartedEvent') {
		var tableId = event.tableID.internal
		session.tables[tableId].gameId = event.gameID.internal
		createTableIfMissing(tableId)
		displayGameStarted(event)
		getBalance(displayBalance)
	}	
	displaySession()
}

function createTableIfMissing(tableId) {
	if (typeof session.tables[tableId] === 'undefined') {
		session.currentTableId = tableId
		session.tables[tableId] = {
			cards : [],
			opponentCards : 0
		}
	}
}

function leaderboardEventListener(event) {
	getBalance(displayBalance)
	displayLeaderboard(JSON.parse(event.data))
}

function queryResponseListener(response) {
	var content = JSON.parse(response.data)
	if (content.type == 'TablesDTO') {
		tables = content.tablesWithPlayers
		$('#welcomeDiv').hide()
		displayTables()
	} else {
		console.log('unsupported message',response)
	}
}

//---------------------------------player actions --------------------------
function joinTable() {
	sitToTable($('#tableIdInput').val())
}

function sitToTable(tableId) {
	subscribe('/table/'+tableId, tablePublicEventListener)
	subscribe('/table/'+tableId+'/player/'+playerId, tablePrivateEventListener)
	publish('/command/table/sit',sitToTableCommand(tableId))
}

function playerAct(action) {
	publish('/command/game', gameCommand(session.currentTableId, session.tables[session.currentTableId].gameId, action))
}

//-------------------------------------commands&queries--------------------------
function tablesQuery() {
	return JSON.stringify( {playerId : playerId} )
}
function sitToTableCommand(tableId) {
	return JSON.stringify({
		playerId : playerId,
		tableId : tableId
	})
}
function gameCommand(tableId, gameId, action) {
	return JSON.stringify({
		playerId : playerId,
		gameId : gameId,
		action : action
	})
}
