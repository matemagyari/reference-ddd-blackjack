package org.home.blackjack.core.infrastructure.integration.cometd;

import java.util.List;
import java.util.Map;

import org.cometd.bayeux.Channel;
import org.cometd.bayeux.Message;
import org.cometd.bayeux.client.ClientSessionChannel;
import org.cometd.bayeux.client.ClientSessionChannel.MessageListener;
import org.cometd.client.BayeuxClient;
import org.cometd.client.transport.LongPollingTransport;
import org.eclipse.jetty.client.HttpClient;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class CometDClient {

    private final BayeuxClient client;
    private final EchoListener echoListener = new EchoListener();
    private String url = "http://0.0.0.0:9099/cometd";
    
    private final Map<String, List<Message>>  messageBuffer = Maps.newHashMap();
    
    public static void main(String... args) {
        CometDClient cometDClient = new CometDClient();
        cometDClient.run();
    }

    public CometDClient() {
        HttpClient httpClient = new HttpClient();
        try {
            httpClient.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        client = new BayeuxClient(url, new LongPollingTransport(null, httpClient));
    }
    
    public void reset() {
    	messageBuffer.clear();
    }

    public void run() {

        client.getChannel(Channel.META_HANDSHAKE).addListener(new InitializerListener());
        client.getChannel(Channel.META_CONNECT).addListener(new ConnectionListener());

        client.handshake();
        boolean success = client.waitFor(1000, BayeuxClient.State.CONNECTED);
        if (!success) {
            System.err.printf("Could not handshake with server at %s%n", url);
            return;
        }

    }
    
    public void subscribeToChannel(String channelName, ClientSessionChannel.MessageListener listener) {
    	ClientSessionChannel channel = client.getChannel(channelName);
    	channel.subscribe(listener);
    }

    public void subscribeToChannel(final String channelName) {
    	MessageListener listener = new MessageListener() {
			
			@Override
			public void onMessage(ClientSessionChannel channel, Message msg) {
				List<Message> messages = messageBuffer.get(channelName);
				if (messages == null) {
					messages = Lists.newArrayList();
					messageBuffer.put(channelName, messages);
				}
				messages.add(msg);
			}
		};
    	ClientSessionChannel channel = client.getChannel(channelName);
    	channel.subscribe(listener);
    }
    

	public void waitForMessage(String channel) {
		messageBuffer.get(channel);
	}
	
    private void initialize() {
        client.batch(new Runnable() {
            public void run() {
                ClientSessionChannel outChannel = client.getChannel("/inchannel");
                ClientSessionChannel inChannel = client.getChannel("/outchannel");
                inChannel.subscribe(echoListener);

                // Map<String, Object> data = new HashMap<String, Object>();
                // data.put("user", nickname);
                // data.put("membership", "join");
                // data.put("chat", nickname + " has joined");
                outChannel.publish("hello");
            }
        });
    }

    private class EchoListener implements ClientSessionChannel.MessageListener {
        public void onMessage(ClientSessionChannel channel, Message message) {
            System.err.println("echoListener " + message);
        }
    }

    private class InitializerListener implements ClientSessionChannel.MessageListener {
        public void onMessage(ClientSessionChannel channel, Message message) {
            if (message.isSuccessful()) {
                initialize();
            }
        }
    }
    private class ConnectionListener implements ClientSessionChannel.MessageListener {
        private boolean wasConnected;
        private boolean connected;

        public void onMessage(ClientSessionChannel channel, Message message) {
            if (client.isDisconnected()) {
                connected = false;
                connectionClosed();
                return;
            }

            wasConnected = connected;
            connected = message.isSuccessful();
            if (!wasConnected && connected) {
                connectionEstablished();
            } else if (wasConnected && !connected) {
                connectionBroken();
            }
        }
    }

    private void connectionEstablished() {
        System.err.printf("system: Connection to Server Opened%n");
        // Map<String, Object> data = new HashMap<String, Object>();
        // data.put("user", nickname);
        // data.put("room", "/chat/demo");
        // client.getChannel("/service/members").publish(data);
    }

    private void connectionClosed() {
        System.err.printf("system: Connection to Server Closed%n");
    }

    private void connectionBroken() {
        System.err.printf("system: Connection to Server Broken%n");
    }

}
