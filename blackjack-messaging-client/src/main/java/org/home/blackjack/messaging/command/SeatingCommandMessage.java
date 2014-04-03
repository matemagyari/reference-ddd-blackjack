package org.home.blackjack.messaging.command;

import org.home.blackjack.messaging.common.Message;


public class SeatingCommandMessage extends Message {
	
	public final String tableId;
	public final String playerId;

	public SeatingCommandMessage(String playerId, String tableId) {
		this.playerId = playerId;
		this.tableId = tableId;
	}
}
