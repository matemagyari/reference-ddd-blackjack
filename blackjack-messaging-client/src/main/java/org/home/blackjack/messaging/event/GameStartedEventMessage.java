package org.home.blackjack.messaging.event;

import org.home.blackjack.messaging.common.Message;

public class GameStartedEventMessage extends Message {

    public final String tableID;
    public final String gameID;
    public final String playerToAct;
    public final int sequenceNumber;

    public GameStartedEventMessage(String gameID, String tableID, String playerToAct, int sequenceNumber) {
        this.gameID = gameID;
        this.tableID = tableID;
		this.playerToAct = playerToAct;
        this.sequenceNumber = sequenceNumber;
    }

}
