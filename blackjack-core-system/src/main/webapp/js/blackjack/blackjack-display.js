
//--------------------------------- display logic -----------------------
function displayGameStarted(event) {
	$('#playDiv').show()
	console.log('displayGameStarted', event)
}
function displayTable(event) {
	console.log('displayTable', event)
}
function displayOpponentsCard(event) {
	console.log('displayOpponentsCard', event)
}
function displayNewCard(event) {
	console.log('displayNewCard', event)
}
function displayLeaderboard(event) {
	$('#leaderBoardDiv').show()
	$('#leaderboardTable').empty()
	$('#leaderboardTable').append('<tr><td>Player</td><td>Wins</td></tr>')
	for (var i = 0; i < event.records.length; i++) {
		var record = event.records[i]
		$('#leaderboardTable').append('<tr><td>'+record.playerName.text+'</td><td>'
			+ record.winNumber+'</td></tr>')
	}
}

function displayTables() {
	$('#tableDiv').show()
	$('#tablesTable').empty()
	$('#tablesTable').append('<tr><td>Table</td><td>Players</td><td></td></tr>')
	for(tableId in tables) { 
		var players = tables[tableId]
		var playerSitsHere = false
		if (typeof session.tables[tableId] != 'undefined') {
			players = formatPlayers(session.tables[tableId].players)
			playerSitsHere = containsPlayer(session.tables[tableId].players)
		}
		var buttonStr = '<input type="button" value="Join" onclick="sitToTable(\''+tableId+'\')" />'
		if (playerSitsHere) {
			buttonStr = ''
		}
		$('#tablesTable').append('<tr><td>'+tableId+'</td><td>'+players+'</td><td>'+buttonStr+'</td></tr>')
	}
}

function displayGameFinished() {
	
}

function displaySession() {
	$('#actualTableSelect').change(displayCards)
	if ($('#actualTableSelect').length == 0 ) {
		$('#actualTableSelect').append('<option value="NONE">NONE</option>')	
	}
	for(tableId in session.tables) {
		if ($('#actualTableSelect option[value="'+tableId+'"]').length == 0 ) { //if table is new
			$('#actualTableSelect').append('<option value="'+tableId+'">'+tableId+'</option>')
		}
	}
	
	displayCards()
}

function displayCards() {
	var optionSelected = $('#actualTableSelect').find("option:selected")
    session.currentTableId  = optionSelected.val()
    var tableSession = session.tables[session.currentTableId]
    if (typeof tableSession != 'undefined') {
    	var cardsStr = formatCards(tableSession.cards)
    	$('#cards').val(cardsStr)
    	$('#opponentsCards').val(tableSession.opponentCards)	
	}
}

function formatCards(cards) {
	var str = ""
	for (var i = 0; i < cards.length; i++) {
		str = str + formatCard(cards[i]) + ' '	
	}
	return str
}

function formatCard(card) {
	var suiteMap = {
			'CLUB' : 'c',
			'SPADE' : 's',
			'HEART' : 'h',
			'DIAMOND' : 'd'
	}
	var rankMap = {
			'TWO' : '2',
			'THREE' : 's',
			'FOUR' : '4',
			'FIVE' : '5',
			'SIX' : '6',
			'SEVEN' : '7',
			'EIGHT' : '8',
			'NINE' : '9',
			'TEN' : '10',
			'JACK' : 'J',
			'QUEEN' : 'Q',
			'KING' : 'K',
			'ACE' : 'A'
	}
	return rankMap[card.rank] + suiteMap[card.suite]
}

function formatPlayers(players) {
	var str = ""
	for (var i = 0; i < players.length; i++) {
		var thePlayer = players[i].internal
		if (thePlayer === session.playerId) {
			thePlayer = 'me'
		}
		str = str + thePlayer + ' '	
	}
	return str	
}

function containsPlayer(players) {
	for (var i = 0; i < players.length; i++) {
		if (session.playerId === players[i].internal)
			return true
	}
	return false	
}


function displayBalance(balance) {
	var amount = balance.substring(balance.indexOf('amount=')+7,balance.length-1)
	$('#balanceDiv').show()
	$('#balanceInput').val(amount)
}

function displayName() {
	$('#nameDiv').show()
	$('#nameInput').val(session.name)
}