package org.home.blackjack.domain.game.event;

import org.home.blackjack.domain.game.core.GameId;

public class InitalCardsDealtEvent extends GameEvent {

	public InitalCardsDealtEvent(GameId gameId, int sequenceNumber) {
		super(gameId, sequenceNumber);
	}

}
