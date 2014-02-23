package org.home.blackjack.infrastructure.persistence.game.store.mongo;

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
}
