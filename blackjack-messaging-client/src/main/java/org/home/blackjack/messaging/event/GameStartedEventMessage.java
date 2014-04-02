package org.home.blackjack.messaging.event;

public class GameStartedEventMessage {

    public final String tableID;
    public final String gameID;
    public final int sequenceNumber;

    public GameStartedEventMessage(String gameID, String tableID, int sequenceNumber) {
        this.gameID = gameID;
        this.tableID = tableID;
        this.sequenceNumber = sequenceNumber;
    }

}
