package org.home.blackjack.core.infrastructure.persistence.game.store.inmemory;

import org.home.blackjack.core.domain.game.Game;
import org.home.blackjack.core.infrastructure.persistence.shared.core.PersistenceObject;

public class InMemoryPersistenceGame implements PersistenceObject<Game> {
	
	private final InMemoryPersistenceGameId id;
	private final String json;
	
	public InMemoryPersistenceGame(InMemoryPersistenceGameId id, String json) {
		this.id = id;
		this.json = json;
	}
	
	public String getJson() {
		return json;
	}

	@Override
	public InMemoryPersistenceGameId id() {
		return id;
	}

}
