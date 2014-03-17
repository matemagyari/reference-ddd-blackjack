
/**
 * Callback function - Cometd consumer.
 */

var cometd = $.cometd
function init() {
    console.log("done loading js");
    
    
    cometd.addListener('/meta/handshake', metaHandshakeListener);
    cometd.addListener('/meta/connect', metaConnectListener);
    cometd.unregisterTransport('websocket');
	cometd.configure({
        url: 'http://localhost:9099/cometd'
    });
    cometd.handshake(metaHandshakeListener);	
    
}

function msgListener(msg) {
  console.log('received message from server: ', msg.data);
}

function metaHandshakeListener(msg){
	console.log('handshake message: ', msg)

	if (msg.successful) {
		console.log('handshake success: ', msg)

    }
}
function metaConnectListener(msg){
	console.log('connect message: ', msg); 

	if (msg.successful) {
    	console.log('connect success: ', msg); 
		cometd.subscribe('/outchannel', msgListener)
		console.log('subscribed')
		cometd.publish('/inchannel','dadada')
		console.log('published')
    }
}