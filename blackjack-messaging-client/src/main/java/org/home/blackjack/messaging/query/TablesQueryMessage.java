package org.home.blackjack.messaging.query;

import org.home.blackjack.messaging.common.Message;


public class TablesQueryMessage extends Message {
    
    public final String playerId;

    public TablesQueryMessage(String playerID) {
        this.playerId = playerID;
    }
}
