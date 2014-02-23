package org.home.blackjack.infrastructure.persistence.game.store.inmemory;

import java.util.Map;

import org.home.blackjack.domain.game.Game;
import org.home.blackjack.domain.game.core.GameID;
import org.home.blackjack.infrastructure.persistence.game.store.GameStore;
import org.home.blackjack.infrastructure.persistence.shared.PersistenceObject;
import org.home.blackjack.infrastructure.persistence.shared.PersistenceObjectId;

import com.google.common.collect.Maps;

public class InMemoryGameStore implements GameStore {
	
	private final Map<InMemoryPersistenceGameId, String> jsonMap = Maps.newHashMap();

	private InMemoryGamePersistenceAssembler gameStoreAssembler;
	
	@Override
	public InMemoryGamePersistenceAssembler assembler() {
		return gameStoreAssembler;
	}

	@Override
	public InMemoryPersistenceGame find(PersistenceObjectId<GameID> id) {
		InMemoryPersistenceGameId gameID = (InMemoryPersistenceGameId) id;
		String json = jsonMap.get(gameID);
		return new InMemoryPersistenceGame(gameID, json);
	}

	@Override
	public void update(PersistenceObject<Game> po) {
		InMemoryPersistenceGame mpg = (InMemoryPersistenceGame) po;
		jsonMap.put(mpg.id(), mpg.getJson());
	}
}
