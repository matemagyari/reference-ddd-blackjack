package org.home.blackjack.core.infrastructure.persistence.game.store.inmemory;

import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.annotation.Resource;
import javax.inject.Named;

import org.home.blackjack.core.domain.game.Game;
import org.home.blackjack.core.domain.game.core.GameID;
import org.home.blackjack.core.domain.shared.TableID;
import org.home.blackjack.core.infrastructure.persistence.game.store.GameStore;
import org.home.blackjack.core.infrastructure.persistence.shared.PersistenceObject;
import org.home.blackjack.core.infrastructure.persistence.shared.PersistenceObjectId;

import com.google.common.collect.Maps;

@Named
public class InMemoryGameStore implements GameStore {
	
	private final Map<InMemoryPersistenceGameId, String> jsonMap = Maps.newHashMap();
	private final ConcurrentMap<GameID, Lock> locks = Maps.newConcurrentMap();

	@Resource
	private InMemoryGamePersistenceAssembler gameStoreAssembler;
	
	public InMemoryGameStore() {
    }
	
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
	public PersistenceObject<Game> find(TableID tableId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(PersistenceObject<Game> po) {
		InMemoryPersistenceGame mpg = (InMemoryPersistenceGame) po;
		jsonMap.put(mpg.id(), mpg.getJson());
	}

    @Override
    public void create(PersistenceObject<Game> po) {
        InMemoryPersistenceGame mpg = (InMemoryPersistenceGame) po;
        jsonMap.put(mpg.id(), mpg.getJson());
    }

    @Override
    public Lock getLockForKey(GameID key) {
        locks.putIfAbsent(key, new ReentrantLock());
        return locks.get(key);
    }

	@Override
	public void clear() {
		jsonMap.clear();
		locks.clear();
	}

}
