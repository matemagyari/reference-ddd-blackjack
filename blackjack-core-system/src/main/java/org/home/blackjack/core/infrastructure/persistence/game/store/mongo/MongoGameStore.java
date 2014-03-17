package org.home.blackjack.core.infrastructure.persistence.game.store.mongo;

import java.util.Map;
import java.util.concurrent.locks.Lock;

import org.home.blackjack.core.domain.game.Game;
import org.home.blackjack.core.domain.game.core.GameID;
import org.home.blackjack.core.domain.shared.TableID;
import org.home.blackjack.core.infrastructure.persistence.game.store.GameStore;
import org.home.blackjack.core.infrastructure.persistence.shared.PersistenceObject;
import org.home.blackjack.core.infrastructure.persistence.shared.PersistenceObjectId;

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
	public MongoPersistenceGame find(TableID id) {
		return null;
	}
	
	@Override
	public void update(PersistenceObject<Game> po) {
		MongoPersistenceGame mpg = (MongoPersistenceGame) po;
		jsonMap.put(mpg.id(), mpg.getJson());
		
	}
	@Override
	public void create(PersistenceObject<Game> po) {
	    MongoPersistenceGame mpg = (MongoPersistenceGame) po;
	    jsonMap.put(mpg.id(), mpg.getJson());
	}
	
    @Override
    public Lock getLockForKey(GameID key) {
        return null;
    }

	@Override
	public void clear() {
		jsonMap.clear();
	}
}
