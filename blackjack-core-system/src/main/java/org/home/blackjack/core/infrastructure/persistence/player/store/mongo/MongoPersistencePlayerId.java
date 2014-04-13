package org.home.blackjack.core.infrastructure.persistence.player.store.mongo;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.home.blackjack.core.domain.shared.PlayerID;
import org.home.blackjack.core.infrastructure.persistence.shared.core.PersistenceObjectId;

public class MongoPersistencePlayerId implements PersistenceObjectId<PlayerID> {
	
	private final String id;
	
	public MongoPersistencePlayerId(String id) {
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
