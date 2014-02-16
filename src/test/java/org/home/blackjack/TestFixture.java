package org.home.blackjack;

import java.util.concurrent.atomic.AtomicLong;

import org.home.blackjack.domain.core.Card;
import org.home.blackjack.domain.core.PlayerId;
import org.home.blackjack.domain.game.Deck;

public class TestFixture {
	
	private static AtomicLong idGenerator = new AtomicLong();

	public static PlayerId aPlayer() {
		return new PlayerId(idGenerator.getAndIncrement());
	}

}
