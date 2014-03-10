package org.home.blackjack.core.domain.game.event;

import org.home.blackjack.core.domain.game.core.GameID;
import org.home.blackjack.core.domain.shared.TableID;

public class GameStartedEvent extends GameEvent {

	public GameStartedEvent(GameID gameID, TableID tableId, int sequenceNumber) {
		super(gameID, tableId, null, sequenceNumber);
	}

}
