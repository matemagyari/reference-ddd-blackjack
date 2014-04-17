package org.home.blackjack.core.infrastructure.persistence.game.store.inmemory;

import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.inject.Named;

import org.home.blackjack.core.domain.game.Game;
import org.home.blackjack.core.domain.game.core.GameID;
import org.home.blackjack.core.domain.shared.TableID;
import org.home.blackjack.core.infrastructure.persistence.game.store.GameStore;
import org.home.blackjack.core.infrastructure.persistence.game.store.json.GameGsonProvider;
import org.home.blackjack.core.infrastructure.persistence.shared.json.JsonPersistenceAssembler;
import org.home.blackjack.core.infrastructure.persistence.shared.json.JsonPersistenceObject;
import org.home.blackjack.core.infrastructure.persistence.shared.json.StringPersistenceId;
import org.home.blackjack.util.ddd.pattern.infrastructure.persistence.PersistenceObject;
import org.home.blackjack.util.ddd.pattern.infrastructure.persistence.PersistenceObjectId;

import com.google.common.collect.Maps;

@Named
public class InMemoryGameStore implements GameStore {
	
	private final Map<StringPersistenceId<GameID>, String> jsonMap = Maps.newHashMap();
	private final ConcurrentMap<GameID, Lock> locks = Maps.newConcurrentMap();

	private JsonPersistenceAssembler<Game> gameStoreAssembler = new JsonPersistenceAssembler<Game>(Game.class, new GameGsonProvider());
	
	public InMemoryGameStore() {
    }
	
	@Override
	public JsonPersistenceAssembler<Game> assembler() {
		return gameStoreAssembler;
	}

	@Override
	public JsonPersistenceObject<Game> find(PersistenceObjectId<GameID> id) {
		StringPersistenceId<GameID> gameID = (StringPersistenceId<GameID>) id;
		String json = jsonMap.get(gameID);
		return new JsonPersistenceObject<Game>(json);
	}
	

	@Override
	public PersistenceObject<Game> find(TableID tableId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(PersistenceObject<Game> po) {
		JsonPersistenceObject<Game> mpg = (JsonPersistenceObject<Game>) po;
		jsonMap.put((StringPersistenceId<GameID>)mpg.id(), mpg.getJson());
	}

    @Override
    public void create(PersistenceObject<Game> po) {
        JsonPersistenceObject<Game> mpg = (JsonPersistenceObject<Game>) po;
        jsonMap.put((StringPersistenceId<GameID>)mpg.id(), mpg.getJson());
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
