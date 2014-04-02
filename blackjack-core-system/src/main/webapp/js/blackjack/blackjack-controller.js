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

function afterRegistration(aPlayerId, name) {
	session.playerId = aPlayerId
	session.name = name
	subscribe('/leaderboard', leaderboardEventListener)
	subscribe('/player/'+session.playerId+'/query/response', queryResponseListener)
	publish('/query/request',tablesQuery())
	getBalance(displayBalance)
	displayName()
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
	var theTableId = null;
	if (event.type == 'PlayerSeatedEvent') {
		if (event.player.internal === session.playerId) {

		}
	} else if (event.type == 'PublicPlayerCardDealtEvent') {
		theTableId = event.tableID.internal
		createTableIfMissing(theTableId)
		if (event.actingPlayer.internal != session.playerId) {
			session.tables[theTableId].opponentCards = session.tables[theTableId].opponentCards + 1
			console.log('opponents card', session.tables[theTableId].opponentCards)
		} else {
			console.log('my card', event)
		}
		displayOpponentsCard(event)
	} else if (event.type == 'TableSeatingChangedEvent') {
		theTableId = event.id.internal
		createTableIfMissing(theTableId)
		session.tables[theTableId].players = event.players		
		displayTable(event)
	} else if (event.type == 'GameStartedEvent') {
		theTableId = event.tableID.internal
		createTableIfMissing(theTableId)
		session.tables[theTableId].gameId = event.gameID.internal
		displayGameStarted(event)
		getBalance(displayBalance)
	} else if (event.type == 'GameFinishedEvent') {
		session.tables[event.tableID.internal] = {}
	}
	if (typeof session.currentTableId === 'undefined') {
		session.currentTableId = theTableId
	}
	displayTables()	
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
	subscribe('/table/'+tableId+'/player/'+session.playerId, tablePrivateEventListener)
	publish('/command/table/sit',sitToTableCommand(tableId))
}

function playerAct(action) {
	publish('/command/game', gameCommand(session.currentTableId, session.tables[session.currentTableId].gameId, action))
}

//-------------------------------------commands&queries--------------------------
function tablesQuery() {
	return JSON.stringify( {playerId : session.playerId} )
}
function sitToTableCommand(tableId) {
	return JSON.stringify({
		playerId : session.playerId,
		tableId : tableId
	})
}
function gameCommand(tableId, gameId, action) {
	return JSON.stringify({
		playerId : session.playerId,
		gameId : gameId,
		action : action
	})
}
