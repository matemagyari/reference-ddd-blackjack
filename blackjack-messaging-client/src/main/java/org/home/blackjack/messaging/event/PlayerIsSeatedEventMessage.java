package org.home.blackjack.messaging.event;

import org.home.blackjack.messaging.common.Message;

public class PlayerIsSeatedEventMessage extends Message {

    public final String tableID;
    public final String playerId;

    public PlayerIsSeatedEventMessage(String tableID, String playerId) {
        this.tableID = tableID;
        this.playerId = playerId;
    }

}
