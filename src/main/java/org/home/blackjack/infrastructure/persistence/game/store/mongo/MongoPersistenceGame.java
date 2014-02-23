package org.home.blackjack.infrastructure.persistence.game.store.mongo;

import org.home.blackjack.domain.game.Game;
import org.home.blackjack.infrastructure.persistence.shared.PersistenceObject;

public class MongoPersistenceGame implements PersistenceObject<Game> {
	
	private final MongoPersistenceGameId id;
	private final String json;
	
	public MongoPersistenceGame(MongoPersistenceGameId id, String json) {
		this.id = id;
		this.json = json;
	}
	
	public String getJson() {
		return json;
	}

	@Override
	public MongoPersistenceGameId id() {
		return id;
	}

}
