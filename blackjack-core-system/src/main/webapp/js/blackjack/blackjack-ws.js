var ws = null
var playerId = null

function register() {
	var playerWS = new WebSocket("ws://localhost:9090/player");
	var registerWS = new WebSocket("ws://localhost:9090/register");
	registerWS.onopen = function() {
		registerWS.send('John');
    };
	registerWS.onmessage = function (evt) { 
       var received_msg = evt.data;
       console.log('Message received...',received_msg);
       playerId = received_msg;
    };
}
function connect() {
  if ("WebSocket" in window)
  {
     alert("WebSocket is supported by your Browser!");
     // Let us open a web socket
     ws = new WebSocket("ws://localhost:9090/echo");
     ws.onopen = function()
     {
        // Web Socket is connected, send data using send()
        var sent_msg = 'sentMessage';
        ws.send(sent_msg);
        console.log('Message is sent...',sent_msg);
     };
     ws.onmessage = function (evt) 
     { 
        var received_msg = evt.data;
        console.log('Message received...',received_msg);
     };
     ws.onclose = function()
     { 
        // websocket is closed.
        alert("Connection is closed..."); 
     };
  }
  else
  {
     // The browser doesn't support WebSocket
     alert("WebSocket NOT supported by your Browser!");
  }
}