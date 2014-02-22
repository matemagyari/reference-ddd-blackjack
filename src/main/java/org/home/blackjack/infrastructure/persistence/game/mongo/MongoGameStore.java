package org.home.blackjack.infrastructure.persistence.game.mongo;

import java.util.Map;

import org.home.blackjack.domain.game.Game;
import org.home.blackjack.domain.game.core.GameID;
import org.home.blackjack.infrastructure.persistence.game.GameStore;
import org.home.blackjack.infrastructure.persistence.shared.PersistenceObject;
import org.home.blackjack.infrastructure.persistence.shared.PersistenceObjectId;

import com.google.common.collect.Maps;

public class MongoGameStore implements GameStore {
	
	private final Map<MongoPersistenceGameId, String> jsonMap = Maps.newHashMap();

	private MongoGamePersistenceAssembler gameStoreAssembler;
	
	@Override
	public MongoGamePersistenceAssembler assembler() {
		return gameStoreAssembler;
	}

	@Override
	public MongoPersistenceGame find(PersistenceObjectId<GameID> id) {
		MongoPersistenceGameId gameID = (MongoPersistenceGameId) id;
		String json = jsonMap.get(gameID);
		return new MongoPersistenceGame(gameID, json);
	}

	@Override
	public void update(PersistenceObject<Game> po) {
		MongoPersistenceGame mpg = (MongoPersistenceGame) po;
		jsonMap.put(mpg.id(), mpg.getJson());
		
	}


}
