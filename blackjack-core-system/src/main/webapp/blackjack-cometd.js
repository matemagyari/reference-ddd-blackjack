google.load("jquery", "1");

/**
 * Callback function - Cometd consumer.
 */
google.setOnLoadCallback(function() {
    $.getScript("http://jquerycomet.googlecode.com/svn/trunk/jquery.comet.js", function(){
    console.log("done loading js");
    $.comet.init("http://localhost:9099/cometd");
    $.comet.subscribe("/outchannel", msgListener);
    $.comet.publish("/inchannel", 'hellooo');
  });
  
});

function msgListener(msg) {
  console.log("received message: " + msg + ", " + msg.data); 
}