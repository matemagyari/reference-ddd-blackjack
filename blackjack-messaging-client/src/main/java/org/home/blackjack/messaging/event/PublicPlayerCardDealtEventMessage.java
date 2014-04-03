package org.home.blackjack.messaging.event;

import org.home.blackjack.messaging.common.Message;


public class PublicPlayerCardDealtEventMessage extends Message {

	public final String tableID;
	public final String gameID;
	public final String actingPlayer;
	public final int sequenceNumber;

	public PublicPlayerCardDealtEventMessage(String gameID, String tableID, String playerId, int sequenceNumber) {
        this.gameID = gameID;
        this.tableID = tableID;
        this.actingPlayer = playerId;
        this.sequenceNumber = sequenceNumber;
	}

}
