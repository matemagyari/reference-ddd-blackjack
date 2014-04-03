package org.home.blackjack.messaging.event;

import org.home.blackjack.messaging.common.Message;

public class InitialCardsDealtEventMessage extends Message {

    public final String tableID;
    public final String gameID;
    public final int sequenceNumber;

    public InitialCardsDealtEventMessage(String gameID, String tableID, int sequenceNumber) {
        this.gameID = gameID;
        this.tableID = tableID;
        this.sequenceNumber = sequenceNumber;
    }

}
