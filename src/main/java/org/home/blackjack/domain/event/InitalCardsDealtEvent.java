package org.home.blackjack.domain.event;

import org.home.blackjack.domain.GameId;

public class InitalCardsDealtEvent extends GameEvent {

	public InitalCardsDealtEvent(GameId gameId, int sequenceNumber) {
		super(gameId, sequenceNumber);
	}

}
