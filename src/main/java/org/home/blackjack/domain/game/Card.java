package org.home.blackjack.domain.game;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Value Object
 * 
 * @author Mate
 * 
 */
public class Card {

	public final Rank rank;
	private final Suite suite;

	public Card(Suite suite, Rank rank) {
		Validate.notNull(suite);
		Validate.notNull(rank);
		this.suite = suite;
		this.rank = rank;
	}

	public static enum Suite {
		CLUB,
		HEART,
		DIAMOND,
		SPADE
	}

	public static enum Rank {

		TWO(2),
		THREE(3),
		FOUR(4),
		FIVE(5),
		SIX(6),
		SEVEN(7),
		EIGHT(8),
		NINE(9),
		TEN(10),
		JACK(10),
		QUEEN(10),
		KING(10),
		ACE(11);

		final int value;

		Rank(int aValue) {
			this.value = aValue;
		}

	}

	public int value() {
		return rank.value;
	}

	@Override
	public boolean equals(Object that) {
		if (!(that instanceof Card))
			return false;
		Card castThat = (Card) that;
		return new EqualsBuilder().append(this.rank, castThat.rank).append(this.suite, castThat.suite).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(rank).append(suite).hashCode();
	}

	@Override
	public String toString() {
		return "[" + rank + " - " + suite + "]";
	}

}
