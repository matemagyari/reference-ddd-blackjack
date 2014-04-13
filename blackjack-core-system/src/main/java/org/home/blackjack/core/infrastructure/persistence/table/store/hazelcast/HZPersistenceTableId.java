package org.home.blackjack.core.infrastructure.persistence.table.store.hazelcast;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.home.blackjack.core.domain.shared.TableID;
import org.home.blackjack.core.infrastructure.persistence.shared.core.PersistenceObjectId;

public class HZPersistenceTableId implements PersistenceObjectId<TableID> {
	
	private final String id;
	
	public HZPersistenceTableId(String id) {
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
