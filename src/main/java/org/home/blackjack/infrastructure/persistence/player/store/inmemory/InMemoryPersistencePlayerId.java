package org.home.blackjack.infrastructure.persistence.player.store.inmemory;

import org.home.blackjack.domain.shared.PlayerID;
import org.home.blackjack.infrastructure.persistence.shared.PersistenceObjectId;

public class InMemoryPersistencePlayerId implements PersistenceObjectId<PlayerID> {
	
	private final String id;
	
	public InMemoryPersistencePlayerId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}
}
