package org.home.blackjack.messaging.query;


public class TablesQueryMessage {
    
    public final String playerId;

    public TablesQueryMessage(String playerID) {
        this.playerId = playerID;
    }
}
