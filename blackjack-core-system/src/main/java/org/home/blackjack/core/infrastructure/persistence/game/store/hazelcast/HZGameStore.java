package org.home.blackjack.core.infrastructure.persistence.game.store.hazelcast;

import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.annotation.Resource;

import org.home.blackjack.core.domain.game.Game;
import org.home.blackjack.core.domain.game.core.GameID;
import org.home.blackjack.core.domain.shared.TableID;
import org.home.blackjack.core.infrastructure.persistence.game.store.GameStore;
import org.home.blackjack.core.infrastructure.persistence.shared.core.PersistenceObject;
import org.home.blackjack.core.infrastructure.persistence.shared.core.PersistenceObjectId;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Maps;
import com.hazelcast.core.HazelcastInstance;

public class HZGameStore implements GameStore {
	
	private final Map<HZPersistenceGameId, String> jsonMap;
	private final ConcurrentMap<GameID, Lock> locks = Maps.newConcurrentMap();

	@Resource
	private HZGamePersistenceAssembler gameStoreAssembler;
	
    @Autowired
    public HZGameStore(HazelcastInstance hzInstance) {
        jsonMap = hzInstance.getMap("gameHZMap");
    }
	
	@Override
	public HZGamePersistenceAssembler assembler() {
		return gameStoreAssembler;
	}

	@Override
	public HZPersistenceGame find(PersistenceObjectId<GameID> id) {
		HZPersistenceGameId gameID = (HZPersistenceGameId) id;
		String json = jsonMap.get(gameID);
		return new HZPersistenceGame(gameID, json);
	}
	

	@Override
	public PersistenceObject<Game> find(TableID tableId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(PersistenceObject<Game> po) {
		HZPersistenceGame mpg = (HZPersistenceGame) po;
		jsonMap.put(mpg.id(), mpg.getJson());
	}

    @Override
    public void create(PersistenceObject<Game> po) {
        HZPersistenceGame mpg = (HZPersistenceGame) po;
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
