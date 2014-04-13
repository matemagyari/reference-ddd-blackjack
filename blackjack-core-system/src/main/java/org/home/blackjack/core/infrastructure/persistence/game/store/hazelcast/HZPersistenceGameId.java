package org.home.blackjack.core.infrastructure.persistence.game.store.hazelcast;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.home.blackjack.core.domain.game.core.GameID;
import org.home.blackjack.core.infrastructure.persistence.shared.core.PersistenceObjectId;

public class HZPersistenceGameId implements PersistenceObjectId<GameID> {
	
	private final String id;
	
	public HZPersistenceGameId(String id) {
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
