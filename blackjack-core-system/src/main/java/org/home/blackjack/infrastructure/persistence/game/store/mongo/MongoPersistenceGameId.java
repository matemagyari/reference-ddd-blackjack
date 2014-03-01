package org.home.blackjack.infrastructure.persistence.game.store.mongo;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.home.blackjack.domain.game.core.GameID;
import org.home.blackjack.infrastructure.persistence.shared.PersistenceObjectId;

public class MongoPersistenceGameId implements PersistenceObjectId<GameID> {
	
	private final String id;
	
	public MongoPersistenceGameId(String id) {
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
