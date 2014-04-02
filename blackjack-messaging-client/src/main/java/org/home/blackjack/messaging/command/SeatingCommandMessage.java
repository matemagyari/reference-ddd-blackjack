package org.home.blackjack.messaging.command;


public class SeatingCommandMessage {
	
	public final String tableId;
	public final String playerId;

	public SeatingCommandMessage(String playerId, String tableId) {
		this.playerId = playerId;
		this.tableId = tableId;
	}
}
