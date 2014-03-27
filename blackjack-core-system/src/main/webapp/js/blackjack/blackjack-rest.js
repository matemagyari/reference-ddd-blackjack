var registrationURI = 'http://localhost:9090/blackjack/register/'
var balanceURI = 'http://0.0.0.0:8080/rest/wallet/account'

function callRegister(name, afterRegistration) {
	$.ajax({
		type : 'POST',
		url : registrationURI + name,
		contentType : 'application/json; charset=utf-8',
		dataType : 'json',
		data : {},
		success : function(anId) {
			console.log('registration success ', anId)
			afterRegistration(anId)
		},
		failure : function(data) {
			console.log('failure', data);
		},
		error : function(request, status, error) {
			console.log("something went wrong \nrequest.responseText : "
					+ request.responseText + "\nstatus: " + status + "\nerror:"
					+ error);
		}
	});
}

function getBalance(playerId, processBalanceResponse) {
	$.ajax({
		type : 'GET',
		url : registrationURI + name,
		contentType : 'application/json; charset=utf-8',
		dataType : 'json',
		data : {},
		success : function(balanceResponse) {
			console.log('registration success ', balanceResponse)
			processBalanceResponse(balanceResponse)
		},
		failure : function(data) {
			console.log('failure', data);
		},
		error : function(request, status, error) {
			console.log("something went wrong \nrequest.responseText : "
					+ request.responseText + "\nstatus: " + status + "\nerror:"
					+ error);
		}
	});
}

function restEchoTest() {
	$.ajax({
		type : 'GET',
		url : 'http://localhost:9090/blackjack/register/echo/test',
		contentType : 'application/json; charset=utf-8',
		dataType : 'plain/text',
		data : {},
		success : function(response) {
			console.log('echo response is ', response)
		},
		failure : function(data) {
			console.log('failure', data);
		},
		error : function(request, status, error) {
			console.log("something went wrong \nrequest.responseText : "
					+ request.responseText + "\nstatus: " + status + "\nerror:"
					+ error);
		}
	});	
}