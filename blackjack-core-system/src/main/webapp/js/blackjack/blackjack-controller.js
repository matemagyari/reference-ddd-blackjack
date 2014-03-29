var playerId = null

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
	publish('/query/request','aaaa')//tablesQuery())
	publish('/echoin','haho2')
	console.log('afterRegistration', playerId)
}

//------------------------------listeners--------------------------
function tablePrivateEventListener(event) {
	console.log('tablePrivateEventListener', event)
	if (event.type == 'PlayerCardDealt') {
		displayNewCard(event)
	} 
}
function tablePublicEventListener(event) {
	console.log('tablePublicEventListener', event)
	if (event.type == 'PublicPlayerCardDealt') {
		displayOpponentsCard(event)
	} else if (event.type == 'TableSeatingChangedEvent') {
		displayTable(event)
	} else if (event.type == 'GameStartedEvent') {
		displayGameStarted(event)
	}	
}

function leaderboardEventListener(event) {
	displayLeaderboard(event)
}

function queryResponseListener(response) {
	displayTables(response)
}

//---------------------------------player actions --------------------------

function sitToTable(tableId) {
	subscribe('/table/'+tableId, tableEventListener)
	subscribe('/player/'+playerId+'/table/'+tableId, tablePrivateEventListener)
	publish('/command/table/sit',sitToTableCommand(tableId))
}

function playerAct(action, gameId) {
	publish('/command/game', gameCommand(tableId, gameId, action))
}

//-------------------------------------commands&queries--------------------------
function tablesQuery() {
	return {
			playerId : playerId
	} 
}
function sitToTableCommand(tableId) {
	return {
		playerId : playerId,
		tableId : tableId
	}
}
function gameCommand(tableId, gameId, action) {
	return {
		playerId : playerId,
		gameId : gameId,
		action : action
	}
}
