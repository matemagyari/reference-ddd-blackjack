package org.home.blackjack.core.infrastructure.persistence.player.store.inmemory;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.home.blackjack.core.domain.shared.PlayerID;
import org.home.blackjack.core.infrastructure.persistence.shared.PersistenceObjectId;

public class InMemoryPersistencePlayerId implements PersistenceObjectId<PlayerID> {
	
	private final String id;
	
	public InMemoryPersistencePlayerId(String id) {
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
