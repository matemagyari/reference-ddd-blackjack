package org.home.blackjack.core.domain.game.event;

import org.home.blackjack.core.domain.game.core.GameID;
import org.home.blackjack.core.domain.shared.TableID;

public class InitialCardsDealtEvent extends GameEvent {

	public InitialCardsDealtEvent(GameID gameID, TableID tableId, int sequenceNumber) {
		super(gameID, tableId, null, sequenceNumber);
	}

}
