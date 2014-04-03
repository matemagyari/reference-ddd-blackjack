package org.home.blackjack.messaging.event;

import org.home.blackjack.messaging.common.Message;

public class GameStartedEventMessage extends Message {

    public final String tableID;
    public final String gameID;
    public final int sequenceNumber;

    public GameStartedEventMessage(String gameID, String tableID, int sequenceNumber) {
        this.gameID = gameID;
        this.tableID = tableID;
        this.sequenceNumber = sequenceNumber;
    }

}
