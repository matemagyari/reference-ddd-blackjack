package org.home.blackjack.infrastructure.persistence.game.store.inmemory;

import org.home.blackjack.domain.game.core.GameID;
import org.home.blackjack.infrastructure.persistence.shared.PersistenceObjectId;

public class InMemoryPersistenceGameId implements PersistenceObjectId<GameID> {
	
	private final String id;
	
	public InMemoryPersistenceGameId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}
}
