package org.home.blackjack.core.infrastructure.integration.cometd;

import org.cometd.bayeux.Channel;
import org.cometd.bayeux.client.ClientSessionChannel;
import org.cometd.bayeux.client.ClientSessionChannel.ClientSessionChannelListener;
import org.cometd.client.BayeuxClient;
import org.cometd.client.transport.LongPollingTransport;
import org.eclipse.jetty.client.HttpClient;

public abstract class AbstractCometDClient {

	protected final BayeuxClient client;

    public AbstractCometDClient(String baseUrl) {
        HttpClient httpClient = new HttpClient();
        try {
            httpClient.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        client = new BayeuxClient(baseUrl, new LongPollingTransport(null, httpClient));
    }
    
    public void handshake() {

        client.getChannel(Channel.META_HANDSHAKE).addListener(initializerListener());
        client.getChannel(Channel.META_CONNECT).addListener(connectionListener());

        client.handshake();
        boolean success = client.waitFor(1000, BayeuxClient.State.CONNECTED);
        if (!success) {
        	throw new RuntimeException("Could not handshake with server");
        }

    }
    
    protected abstract ClientSessionChannelListener connectionListener();

    protected abstract ClientSessionChannelListener initializerListener();

	public void subscribeToChannel(String channelName, ClientSessionChannel.MessageListener listener) {
    	ClientSessionChannel channel = client.getChannel(channelName);
    	channel.subscribe(listener);
    }

	protected void connectionEstablished() {
	    System.err.printf("system: Connection to Server Opened%n");
	    // Map<String, Object> data = new HashMap<String, Object>();
	    // data.put("user", nickname);
	    // data.put("room", "/chat/demo");
	    // client.getChannel("/service/members").publish(data);
	}

	protected void connectionClosed() {
	    System.err.printf("system: Connection to Server Closed%n");
	}

	protected void connectionBroken() {
	    System.err.printf("system: Connection to Server Broken%n");
	}

}