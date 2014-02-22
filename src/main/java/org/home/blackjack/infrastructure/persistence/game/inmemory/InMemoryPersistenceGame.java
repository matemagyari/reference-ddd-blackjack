package org.home.blackjack.infrastructure.persistence.game.inmemory;

import org.home.blackjack.domain.game.Game;
import org.home.blackjack.infrastructure.persistence.shared.PersistenceObject;

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
