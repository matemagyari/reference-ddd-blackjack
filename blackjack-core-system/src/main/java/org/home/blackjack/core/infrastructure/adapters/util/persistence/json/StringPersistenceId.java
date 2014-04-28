package org.home.blackjack.core.infrastructure.adapters.util.persistence.json;

import java.io.Serializable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.home.blackjack.util.ddd.pattern.domain.model.ID;
import org.home.blackjack.util.ddd.pattern.infrastructure.persistence.PersistenceObjectId;

public class StringPersistenceId <T extends ID> implements PersistenceObjectId<T>, Serializable {

    private static final long serialVersionUID = 2988235216767497988L;
    private final String id;
	
	public StringPersistenceId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
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
