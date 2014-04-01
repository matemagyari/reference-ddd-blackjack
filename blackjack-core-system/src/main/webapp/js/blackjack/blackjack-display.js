
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
		var buttonStr = '<input type="button" value="Join" onclick="sitToTable(\''+tableId+'\')" />'
		$('#tablesTable').append('<tr><td>'+tableId+'</td><td>'+players+'</td><td>'+buttonStr+'</td></tr>')
	}
	console.log('displayTables', tables)
}
function displaySession() {
	$('#sessionDiv').show()
	$('#sessionArea').text(JSON.stringify(session,null,4))
	
	if ($('#actualTableSelect').length == 0 ) {
		$('#actualTableSelect').append('<option value="NONE">NONE</option>')	
	}
	for(tableId in session.tables) {
		if ($('#actualTableSelect option[value="'+tableId+'"]').length == 0 ) { //if table is new
			$('#actualTableSelect').append('<option value="'+tableId+'">'+tableId+'</option>')
		}
	}
	$('#actualTableSelect').change(function () {
    	var optionSelected = $(this).find("option:selected")
    	session.currentTableId  = optionSelected.val()
    	var tableSession = session.tables[session.currentTableId]
    	var cardsStr = formatCards(tableSession.cards)
    	$('#cards').val(cardsStr)
    	$('#opponentsCards').val(tableSession.opponentCards)
 	})
	console.log('session', session)
}

function formatCards(cards) {
	var str = ""
	for (var i = 0; i < cards.length; i++) {
		str = str + cards[i].rank + '/' + cards[i].suite + ' '	
	}
	return str
}

function displayBalance(balance) {
	var amount = balance.substring(balance.indexOf('amount=')+7,balance.length-1)
	$('#balanceDiv').show()
	$('#balanceInput').val(amount)
}