package org.home.blackjack.core.domain.game.event;

import org.home.blackjack.core.domain.game.core.GameID;
import org.home.blackjack.core.domain.table.core.TableID;

public class GameStartedEvent extends GameEvent {

	private final TableID tableId;

	public GameStartedEvent(GameID gameID, int sequenceNumber, TableID tableId) {
		super(gameID, sequenceNumber);
		this.tableId = tableId;
	}

	public TableID tableId() {
		return tableId;
	}
}
