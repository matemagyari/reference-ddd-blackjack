package org.home.blackjack.core.domain.game.core;

import org.apache.commons.lang3.Validate;
import org.home.blackjack.util.ddd.pattern.ValueObject;

public class Card extends ValueObject {

	public final Rank rank;
	public final Suite suite;

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
	public String toString() {
		return "[" + rank + " - " + suite + "]";
	}

}
