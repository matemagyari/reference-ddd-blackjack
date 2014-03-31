package org.home.blackjack.core.infrastructure.integration.cometd;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.cometd.bayeux.Message;
import org.cometd.bayeux.client.ClientSessionChannel;
import org.cometd.bayeux.client.ClientSessionChannel.ClientSessionChannelListener;
import org.cometd.bayeux.client.ClientSessionChannel.MessageListener;
import org.home.blackjack.core.integration.test.util.Util;
import org.junit.Assert;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class CometDClient extends AbstractCometDClient {

    private final EchoListener echoListener = new EchoListener();

    private static Logger LOGGER = Logger.getLogger(CometDClient.class);

    private final Map<String, List<JsonObject>> messageBuffer = Maps.newHashMap();

    public static void main(String... args) {
        CometDClient cometDClient = new CometDClient("http://0.0.0.0:9099/cometd");
        cometDClient.handshake();
    }

    public CometDClient(String baseUrl) {
        super(baseUrl);
    }

    public void reset() {
        messageBuffer.clear();
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
            LOGGER.info("echoListener " + message);
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

    @Override
    protected ClientSessionChannelListener connectionListener() {
        return new ConnectionListener();
    }

    @Override
    protected ClientSessionChannelListener initializerListener() {
        return new InitializerListener();
    }

    public void subscribeAndPublish(String channelToSubscribe, String channelToPublish, String command) {
        subscribeToChannel(channelToSubscribe);
        publish(channelToPublish, command);
    }

    public void publish(String channelToPublish, String command) {
        client.getChannel(channelToPublish).publish(command);
    }

    public <Tt> JsonObject verifyMessageArrived(String channelName, MessageMatcher matcher) {
        for (int i = 0; i < 5; i++) {
            JsonObject message = findMessage(channelName, matcher);
            if (message != null) {
                return message;
            } else {
                Util.sleep(500);
            }
        }
        Assert.fail("event not found on channel " + channelName);
        return null;
    }

    public <T> JsonObject verifyMessageArrived(String channelName, final T expectedEvent) {
        MessageMatcher matcher = new MessageMatcher() {
            public boolean match(JsonObject jsonObject) {
                return Util.equals(expectedEvent, jsonObject);
            }
        };
        return verifyMessageArrived(channelName, matcher);
    }

    private JsonObject findMessage(String channelName, MessageMatcher matcher) {
        JsonObject foundMsg = null;
        synchronized (messageBuffer) {
            List<JsonObject> list = messageBuffer.get(channelName);
            if (list == null) {
                return null;
            }
            for (JsonObject jsonObject : list) {
                if (matcher.match(jsonObject)) {
                    foundMsg = jsonObject;
                    continue;
                }
            }
            if (foundMsg != null) {
                messageBuffer.remove(foundMsg);
            }
        }
        return foundMsg;
    }

    public void subscribeToChannel(final String channelName) {
        // if already subscribed
        if (messageBuffer.keySet().contains(channelName)) {
            return;
        }
        MessageListener listener = new MessageListener() {

            @Override
            public void onMessage(ClientSessionChannel channel, Message msg) {
                JsonObject dataJson = extractData(msg);
                LOGGER.info("msg arrived on [" + channelName + "] - " + dataJson);
                synchronized (messageBuffer) {
                    List<JsonObject> messages = messageBuffer.get(channelName);
                    if (messages == null) {
                        messages = Lists.newArrayList();
                        messageBuffer.put(channelName, messages);
                    }
                    messages.add(dataJson);
                }
            }

        };
        ClientSessionChannel channel = client.getChannel(channelName);
        channel.subscribe(listener);
    }
    
	public String requestReply(final String channelName, String command) {
		final CountDownLatch countDownLatch = new CountDownLatch(1);
        final List<String> response = Lists.newArrayList();
		MessageListener listener = new MessageListener() {
            @Override
            public void onMessage(ClientSessionChannel channel, Message msg) {
                response.add(extractData(msg).toString());
                LOGGER.info("msg arrived on [" + channelName + "] - " + response );
                countDownLatch.countDown();
            }

        };
        subscribeToChannel(channelName, listener);
        publish(channelName, command);
        try {
			countDownLatch.await(1, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		return response.get(0);
	}

    private static JsonObject extractData(Message msg) {
        JsonObject msgJson = (JsonObject) new JsonParser().parse(msg.getJSON());
        String data = msgJson.get("data").toString().replace("\\\"", "\"").replaceFirst("\"", "").replaceAll("}\"", "}");
        return (JsonObject) new JsonParser().parse(data);
    }

    public static interface MessageMatcher {
        boolean match(JsonObject jsonObject);
    }


}
