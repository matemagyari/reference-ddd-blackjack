
/**
 * Callback function - Cometd consumer.
 */

var cometd = $.cometd
function init() {
    console.log("done loading js");
    
    
    cometd.addListener('/meta/handshake', metaHandshakeListener);
    cometd.addListener('/meta/connect', metaConnectListener);
    
	cometd.configure({
        url: 'http://localhost:9099/cometd'
    });
    comet.handshake(metaHandshakeListener);	
}

function msgListener(msg) {
  console.log('received message: ' + msg.data); 
}

function metaHandshakeListener(msg){
	console.log('handshake message: ' + msg.data); 

	if (message.successful) {
    	console.log('handshake success: ' + msg.data); 
    }
}
function metaConnectListener(msg){
	console.log('connect message: ' + msg.data); 

	if (message.successful) {
    	console.log('connect success: ' + msg.data); 
    	
        $.comet.subscribe("/outchannel", msgListener);
        console.log("cometd subscribe");
        
        $.comet.publish("/inchannel", 'hellooo');
        console.log("cometd publish");
    }
}