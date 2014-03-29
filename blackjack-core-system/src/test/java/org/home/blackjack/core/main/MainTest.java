package org.home.blackjack.core.main;

import org.apache.camel.ProducerTemplate;
import org.cometd.bayeux.Message;
import org.cometd.bayeux.client.ClientSessionChannel;
import org.cometd.bayeux.client.ClientSessionChannel.MessageListener;
import org.home.blackjack.core.app.service.registration.RegistrationCommand;
import org.home.blackjack.core.domain.player.PlayerName;
import org.home.blackjack.core.infrastructure.integration.cometd.CometDClient;
import org.junit.Test;

import com.google.gson.Gson;

public class MainTest {

	@Test
	public void test() throws InterruptedException {
		String[] args = new String[] {};
		Main.main(args);

		CometDClient cometDClient = new CometDClient("http://0.0.0.0:9099/cometd");
		cometDClient.handshake();
		cometDClient.subscribeToChannel("/table/1/player/1", new LoggingListener());
		cometDClient.subscribeToChannel("/table/1", new LoggingListener());

		ProducerTemplate producerTemplate = Main.applicationContext.getBean(ProducerTemplate.class);
		producerTemplate.asyncSendBody("cometd://0.0.0.0:9099/table/1/player/1", "private message");
		producerTemplate.asyncSendBody("cometd://0.0.0.0:9099/table/1", "table message");

		Thread.sleep(1000);

		System.err.println("Na what?");

		String channelName = "/service/command/registration";
		cometDClient.subscribeToChannel(channelName, new MessageListener() {

			@Override
			public void onMessage(ClientSessionChannel arg0, Message arg1) {
				System.err.println("hello " + arg1);
			}
		});
		String command = new Gson().toJson(new RegistrationCommand(new PlayerName("aaa")));
		cometDClient.publish(channelName, command);
		Thread.sleep(1000);

		System.err.println("Na what?");
	}

	private static class LoggingListener implements MessageListener {

		@Override
		public void onMessage(ClientSessionChannel channel, Message message) {
			System.err.println("Message on channel " + channel.getChannelId() + ": " + message.getJSON());
		}

	}
}
