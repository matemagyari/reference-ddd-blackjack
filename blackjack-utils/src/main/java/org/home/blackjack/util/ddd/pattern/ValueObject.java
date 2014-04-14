package org.home.blackjack.util.ddd.pattern;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Class defining behaviour of Value Objects, that is to say equals and hashCode based on every field. Every subtype of
 * this class should be immutable.
 * 
 * @author michele.sollecito
 */
public abstract class ValueObject implements Domain {

	@Override
	public final boolean equals(final Object other) {

		return EqualsBuilder.reflectionEquals(this, other);
	}

	@Override
	public final int hashCode() {

		return HashCodeBuilder.reflectionHashCode(this);
	}
}
