package org.home.blackjack.domain.game;

import org.home.blackjack.domain.core.Card;

/**
 * Factory.
 * 
 * Currently it only creates a {@link Deck} of 52 {@link Card}-s, but could be a {@link Deck} of multiple decks.
 * 
 * @author Mate
 *
 */
class DeckFactory {

	public Deck createNew() {
		return Deck.createNew52();
	}
	
}
