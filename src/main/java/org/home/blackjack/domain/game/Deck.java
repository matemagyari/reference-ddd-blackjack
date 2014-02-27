package org.home.blackjack.domain.game;

import java.util.Collections;
import java.util.List;

import org.home.blackjack.domain.game.core.Card;

import com.google.common.collect.Lists;

/**
 * Entity inside {@link Game}. It cannot be referenced outside of the aggregate, lest something could call 'draw' and
 * change the state of the aggregate from outside of it.
 * 
 * Also could have been implemented as a Value Object, but then the 'draw' should return not only a {@link Card}, but
 * also a new {@link Deck} without that {@link Card}
 * 
 * Could have more than 52 {@link Cards}-s, because it can be built up from multiple 52-decks.
 * 
 * @author Mate
 * 
 */
public class Deck {

	private final List<Card> cards;

	public static Deck createNew52() {
		return new Deck();
	}

	// only used for tests
	public static Deck createPrepared(Card... cards) {
		return new Deck(cards);
	}

	// only used for tests
	private Deck(Card... cardsInOrder) {
		this.cards = Lists.newArrayList(cardsInOrder);
	}
	private Deck(List<Card> cards) {
		this.cards = Lists.newArrayList(cards);
	}
	
	private Deck() {
		this.cards = Lists.newArrayList();
		for (Card.Rank rank : Card.Rank.values()) {
			for (Card.Suite suite : Card.Suite.values()) {
				this.cards.add(new Card(suite, rank));
			}
		}
		Collections.shuffle(cards);
	}

	public Card draw() {
		return this.cards.remove(0);
	}

	public void mergeWith(Deck anotherDeck) {
		this.cards.addAll(anotherDeck.cards);
	}

	@Override
	public String toString() {
		return "Deck [cards=" + cards + "]";
	}
	
}
