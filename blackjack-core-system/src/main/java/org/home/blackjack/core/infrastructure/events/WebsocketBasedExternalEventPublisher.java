package org.home.blackjack.core.infrastructure.events;

import java.io.IOException;

import javax.websocket.EndpointConfig;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.home.blackjack.core.app.event.ExternalEventPublisher;
import org.home.blackjack.util.ddd.pattern.events.DomainEvent;

import com.google.gson.GsonBuilder;

@ServerEndpoint(value = "/example", configurator = CustomConfigurator.class)
public class WebsocketBasedExternalEventPublisher implements ExternalEventPublisher {

	private final GsonBuilder gsonBuilder = new GsonBuilder();
	private Session session;

	@OnOpen
	public void open(Session session, EndpointConfig config) {
		this.session = session;
	}

	@Override
	public void publish(DomainEvent event) {
		String json = gsonBuilder.create().toJson(event);
		System.err.println("Publish: " + json);
		try {
			session.getBasicRemote().sendText(json);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
