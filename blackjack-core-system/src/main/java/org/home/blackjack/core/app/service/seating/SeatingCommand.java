package org.home.blackjack.core.app.service.seating;

import org.home.blackjack.core.domain.shared.PlayerID;
import org.home.blackjack.core.domain.shared.TableID;

public class SeatingCommand {
	
	private final String tableId;
	private final String playerId;

	public SeatingCommand(String playerId, String tableId) {
		this.playerId = playerId;
		this.tableId = tableId;
	}
	
	public TableID getTableId() {
		return TableID.createFrom(tableId);
	}
	public PlayerID getPlayerId() {
		return PlayerID.createFrom(playerId);
	}

	@Override
	public String toString() {
		return "SeatingCommand [tableId=" + tableId + ", playerId=" + playerId + "]";
	}
}
