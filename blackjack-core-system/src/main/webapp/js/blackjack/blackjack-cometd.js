
/**
 * Callback function - Cometd consumer.
 */

var cometd = $.cometd
function cometDInit() {
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
	if (msg.successful) {
		
		console.log('handshake success: ', msg)
		subscribe('/echoout', msgListener)
		publish('/echoin','dadada')
		
		cometDInitialized();
    } else {
    	console.log('handshake failed: ', msg)
    }
}
function metaConnectListener(msg){
	if (msg.successful) {
    	console.log('meta connect success: ', msg); 
    } else {
    	console.log('meta connect failed: ', msg); 
    }
}

function subscribe(channel, msgListener) {
	cometd.subscribe(channel, msgListener)
}
function publish(channel, msg) {
	cometd.publish(channel,msg)
}