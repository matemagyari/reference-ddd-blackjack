package org.home.blackjack.messaging.event;

import org.home.blackjack.messaging.common.Message;

public class GameFinishedEventMessage extends Message {

    public final String tableID;
    public final String gameID;
    public final String winner;
    public final int sequenceNumber;

    public GameFinishedEventMessage(String gameID, String tableID, String playerId, int sequenceNumber) {
        this.gameID = gameID;
        this.tableID = tableID;
        this.winner = playerId;
        this.sequenceNumber = sequenceNumber;
    }

}
