package org.home.blackjack.domain;

/**
 * Factory.
 * 
 * Currently it only creates a {@link Deck} of 52 {@link Card}-s, but could be a {@link Deck} of multiple decks.
 * 
 * @author Mate
 *
 */
public class DeckFactory {

	public Deck createNew() {
		return Deck.createNew52();
	}
	
}
