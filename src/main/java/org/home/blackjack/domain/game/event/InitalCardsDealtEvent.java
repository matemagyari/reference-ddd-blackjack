package org.home.blackjack.domain.game.event;

import org.home.blackjack.domain.game.GameID;

public class InitalCardsDealtEvent extends GameEvent {

	public InitalCardsDealtEvent(GameID gameID, int sequenceNumber) {
		super(gameID, sequenceNumber);
	}

}
