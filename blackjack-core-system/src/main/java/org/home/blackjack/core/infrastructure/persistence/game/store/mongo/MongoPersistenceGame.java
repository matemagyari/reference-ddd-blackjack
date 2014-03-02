package org.home.blackjack.core.infrastructure.persistence.game.store.mongo;

import org.home.blackjack.core.domain.game.Game;
import org.home.blackjack.core.infrastructure.persistence.shared.PersistenceObject;

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
