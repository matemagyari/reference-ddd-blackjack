package org.home.blackjack.messaging.common;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public abstract class Message {
	
	@Override
	public boolean equals(final Object other) {
		return EqualsBuilder.reflectionEquals(this, other);
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

}
