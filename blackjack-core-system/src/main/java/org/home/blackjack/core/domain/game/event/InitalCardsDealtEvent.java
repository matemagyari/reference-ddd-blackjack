package org.home.blackjack.core.domain.game.event;

import org.home.blackjack.core.domain.game.core.GameID;

public class InitalCardsDealtEvent extends GameEvent {

	public InitalCardsDealtEvent(GameID gameID, int sequenceNumber) {
		super(gameID, sequenceNumber);
	}

}
