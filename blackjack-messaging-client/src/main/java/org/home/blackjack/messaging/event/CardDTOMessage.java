package org.home.blackjack.messaging.event;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class CardDTOMessage {

	public final String rank;
	public final String suite;

	public CardDTOMessage(String rank, String suite) {
		this.rank = rank;
		this.suite = suite;
	}

	@Override
	public final boolean equals(final Object other) {
		return EqualsBuilder.reflectionEquals(this, other);
	}

	@Override
	public final int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

}
