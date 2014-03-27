var registrationURI = 'http://0.0.0.0:9090/blackjack/register/'
var balanceURI = 'http://0.0.0.0:8080/rest/wallet/account'

var playerId = null

function register(name, afterRegistration) {
	$.ajax({
		type : 'POST',
		url : registrationURI + name,
		contentType : 'application/json; charset=utf-8',
		dataType : 'json',
		data : {},
		success : function(anId) {
			console.log('registration success ', anId)
			playerId = anId
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

function getBalance(playerId) {
	$.ajax({
		type : 'GET',
		url : registrationURI + name,
		contentType : 'application/json; charset=utf-8',
		dataType : 'json',
		data : {},
		success : function(anId) {
			console.log('registration success ', anId)
			playerId = anId
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