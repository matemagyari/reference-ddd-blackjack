package org.home.blackjack.messaging.command;

import org.home.blackjack.messaging.common.Message;

public class GameCommandMessage extends Message {

    public final String action;
    public final String gameId;
    public final String playerId;

    public GameCommandMessage(String playerId, String gameId, String action) {
        this.playerId = playerId;
        this.gameId = gameId;
        this.action = action;
    }
}
