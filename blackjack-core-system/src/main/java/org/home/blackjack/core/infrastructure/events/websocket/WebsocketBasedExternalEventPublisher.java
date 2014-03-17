package org.home.blackjack.core.infrastructure.events.websocket;

import java.io.IOException;
import java.util.Map;

import javax.annotation.Resource;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.home.blackjack.core.app.events.external.ExternalDomainEvent;
import org.home.blackjack.core.app.events.external.ExternalEventPublisher;
import org.home.blackjack.core.app.service.game.GameActionApplicationService;
import org.home.blackjack.util.ddd.pattern.events.DomainEvent;

import com.google.common.collect.Maps;
import com.google.gson.GsonBuilder;

@ServerEndpoint(value = "/example", configurator = CustomConfigurator.class)
public class WebsocketBasedExternalEventPublisher implements ExternalEventPublisher {
    
    @Resource
    private GameActionApplicationService gameActionApplicationService;

    private final GsonBuilder gsonBuilder = new GsonBuilder();
    //there must be sessions for clients
    private final Map<String, Session> sessions = Maps.newConcurrentMap();

    @OnOpen
    public void onOpen(Session session, EndpointConfig config) {
        this.sessions.put(session.getId(), session);
    }

    @OnClose
    public void onClose(Session session) {
        this.sessions.remove(session.getId());
    }
    @OnMessage
    public void onMessage(String message, Session session) {
        this.sessions.remove(session.getId());
        
    }

    @Override
    public void publish(ExternalDomainEvent event) {
        String json = gsonBuilder.create().toJson(event);
        System.err.println("Publish: " + json);
        /*
        try {
            session.getBasicRemote().sendText(json);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        */
    }

}
