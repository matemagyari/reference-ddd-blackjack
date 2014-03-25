package org.home.blackjack.core.infrastructure.integration.cometd;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.cometd.bayeux.Message;
import org.cometd.bayeux.client.ClientSessionChannel;
import org.cometd.bayeux.client.ClientSessionChannel.ClientSessionChannelListener;
import org.cometd.bayeux.client.ClientSessionChannel.MessageListener;
import org.home.blackjack.core.integration.test.util.Util;
import org.home.blackjack.util.ddd.pattern.events.DomainEvent;
import org.junit.Assert;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class CometDClient extends AbstractCometDClient {

	private final EchoListener echoListener = new EchoListener();

	private static Logger LOGGER = Logger.getLogger(CometDClient.class);

	private final Map<String, List<JsonObject>> messageBuffer = Maps.newHashMap();

	private UUID uuid;

	public static void main(String... args) {
		CometDClient cometDClient = new CometDClient("http://0.0.0.0:9099/cometd");
		cometDClient.handshake();
	}

	public CometDClient(String baseUrl) {
		
		super(baseUrl);
		uuid = UUID.randomUUID();
	}

	public void reset() {
		messageBuffer.clear();
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

	public JsonObject requestAndWaitForReply(String channelToSubscribe, String channelToPublish, String command) {
		final CountDownLatch countDownLatch = new CountDownLatch(1);
		final List<JsonObject> resp = Lists.newArrayList();
		MessageListener listener = new MessageListener() {

			@Override
			public void onMessage(ClientSessionChannel channel, Message msg) {
				JsonObject dataJson = extractData(msg);
				resp.add(dataJson);
				countDownLatch.countDown();
			}
		};
		client.getChannel(channelToSubscribe).subscribe(listener);
		publish(channelToPublish, command);
		try {
			countDownLatch.await(1, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		LOGGER.info("waited enough");
		return resp.get(0);
	}

	public void subscribeAndPublish(String channelToSubscribe, String channelToPublish, String command) {
		subscribeToChannel(channelToSubscribe);
		publish(channelToPublish, command);
	}

	public void publish(String channelToPublish, String command) {
		client.getChannel(channelToPublish).publish(command);
	}
	
	public <T extends DomainEvent> JsonObject verifyMessageArrived(String channelName, MessageMatcher matcher) {
		for(int i = 0;i<5;i++) {
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
	
	public static interface MessageMatcher {
		boolean match(JsonObject jsonObject);
	}

	public <T> JsonObject verifyMessageArrived(String channelName, T expectedEvent) {
		for(int i = 0;i<5;i++) {
			JsonObject message = findMessage(channelName, expectedEvent);
			if (message != null) {
				return message;
			} else {
				Util.sleep(500);
			}
		}
		Assert.fail(expectedEvent + " not found on channel " + channelName);
		return null;
	}

	private <T> JsonObject findMessage(String channelName, T expectedEvent) {
		JsonObject foundMsg = null;
		synchronized (messageBuffer) {
			List<JsonObject> list = messageBuffer.get(channelName);
			if (list==null) {
				return null;
			}
			for (JsonObject jsonObject : list) {
				if (Util.equals(expectedEvent, jsonObject)) {
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

	private <T extends DomainEvent> JsonObject findMessage(String channelName, MessageMatcher matcher) {
		JsonObject foundMsg = null;
		synchronized (messageBuffer) {
			List<JsonObject> list = messageBuffer.get(channelName);
			if (list==null) {
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
		//if already subscribed
		if (messageBuffer.keySet().contains(channelName)) {
			return;
		}
		MessageListener listener = new MessageListener() {

			@Override
			public void onMessage(ClientSessionChannel channel, Message msg) {
				JsonObject dataJson = extractData(msg);
				LOGGER.info("msg arrived on ["+channelName+"] - " + dataJson);
				synchronized (messageBuffer) {
					List<JsonObject> messages = messageBuffer.get(channelName);
					if (messages == null) {
						messages = Lists.newArrayList();
						messageBuffer.put(channelName, messages);
					}
					messages.add(dataJson);
					System.err.println(uuid + " " + messageBuffer.keySet());
				}
			}

		};
		ClientSessionChannel channel = client.getChannel(channelName);
		channel.subscribe(listener);
	}

	private static JsonObject extractData(Message msg) {
		JsonObject msgJson = (JsonObject) new JsonParser().parse(msg.getJSON());
		String data = msgJson.get("data").toString().replace("\\\"", "\"").replaceFirst("\"", "").replaceAll("}\"", "}");
		return (JsonObject) new JsonParser().parse(data);
	}
}
