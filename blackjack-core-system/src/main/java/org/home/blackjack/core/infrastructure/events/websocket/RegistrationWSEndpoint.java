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

import org.home.blackjack.core.domain.RegisterService;
import org.home.blackjack.core.domain.player.PlayerName;
import org.home.blackjack.core.domain.shared.PlayerID;

import com.google.common.collect.Maps;

@ServerEndpoint(value = "/register", configurator = CustomConfigurator.class)
public class RegistrationWSEndpoint {
    
    @Resource
    private RegisterService registerService;

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
        PlayerID playerID = registerService.registerPlayer(new PlayerName(message));
        try {
            session.getBasicRemote().sendText(playerID.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
