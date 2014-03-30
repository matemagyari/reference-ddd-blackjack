
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
	$('#tablesDiv').show()
	$('#tablesArea').text(JSON.stringify(tables,null,4))
	console.log('displayTables', tables)
}
function displaySession() {
	$('#sessionDiv').show()
	$('#sessionArea').text(JSON.stringify(session,null,4))
	console.log('session', session)
}

function displayBalance(balance) {
	var amount = balance.substring(balance.indexOf('amount=')+7,balance.length-1)
	$('#balanceDiv').show()
	$('#balanceInput').val(amount)
}