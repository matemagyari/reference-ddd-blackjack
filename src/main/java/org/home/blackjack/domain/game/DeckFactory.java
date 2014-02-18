package org.home.blackjack.domain.game;

import org.home.blackjack.domain.game.core.Card;

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
	public Deck createNewFromMultipleDecks(int deckNumber) {
		Deck deck = Deck.createNew52();
		for(int i=0; i< deckNumber-1;i++) {
			deck.mergeWith(Deck.createNew52());
		}
		return deck;
	}
}
