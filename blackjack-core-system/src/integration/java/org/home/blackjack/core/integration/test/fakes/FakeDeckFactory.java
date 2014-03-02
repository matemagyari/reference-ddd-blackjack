package org.home.blackjack.core.integration.test.fakes;

import java.util.List;

import org.home.blackjack.core.domain.game.Deck;
import org.home.blackjack.core.domain.game.DeckFactory;
import org.home.blackjack.core.domain.game.core.Card;

import com.google.common.collect.Lists;


public class FakeDeckFactory extends DeckFactory {
	
	private List<Card> preparedCards = Lists.newArrayList();
	
	@Override
	public Deck createNew() {
		return Deck.createPrepared(preparedCards.toArray(new Card [] {}));
	}

	public void prepareDeckFrom(List<Card> cards) {
		preparedCards = Lists.newArrayList(cards);
	}

	public void reset() {
		preparedCards.clear();
	}

}
