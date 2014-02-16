package org.home.blackjack.domain.common;

import org.apache.commons.lang3.builder.HashCodeBuilder;

public abstract class NumericId {
	
	protected final long id;

	public NumericId(long id) {
		this.id = id;
	}

	@Override
	public boolean equals(Object that) {
		if (that == null) return false;
		if (!that.getClass().equals(this.getClass()))
			return false;
		NumericId castThat = (NumericId) that;
		return id == castThat.id;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(id).hashCode();
	}

}
