package org.home.blackjack.core.infrastructure.events.websocket;

import java.io.IOException;

import javax.websocket.EndpointConfig;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/echo", configurator = CustomConfigurator.class)
public class EchoWSEndpoint {

	private Session session;

	@OnOpen
	public void open(Session session, EndpointConfig config) {
		System.err.println("Opened!");
		this.session = session;
	}

	@OnMessage
	public void echo(String msg) throws IOException {
		System.err.println("echo!");
		session.getBasicRemote().sendText(msg + msg);
	}

}
