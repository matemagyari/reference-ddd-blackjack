package org.home.blackjack.messaging.event;


public class PlayerCardDealtEventMessage {

	public final String card;
	public final String tableID;
	public final String gameID;
	public final String actingPlayer;
	public final int sequenceNumber;

	public PlayerCardDealtEventMessage(String gameID, String tableID, String playerId, String card, int sequenceNumber) {
        this.gameID = gameID;
        this.tableID = tableID;
        this.actingPlayer = playerId;
        this.card = card;
        this.sequenceNumber = sequenceNumber;
	}

}
