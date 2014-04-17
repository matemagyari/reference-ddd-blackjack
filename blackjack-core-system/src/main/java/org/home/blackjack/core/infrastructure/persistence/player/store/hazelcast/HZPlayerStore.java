package org.home.blackjack.core.infrastructure.persistence.player.store.hazelcast;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.inject.Named;

import org.home.blackjack.core.domain.player.Player;
import org.home.blackjack.core.domain.shared.PlayerID;
import org.home.blackjack.core.infrastructure.persistence.player.store.PlayerStore;
import org.home.blackjack.core.infrastructure.persistence.player.store.json.PlayerGsonProvider;
import org.home.blackjack.core.infrastructure.persistence.shared.json.JsonPersistenceAssembler;
import org.home.blackjack.core.infrastructure.persistence.shared.json.JsonPersistenceObject;
import org.home.blackjack.core.infrastructure.persistence.shared.json.StringPersistenceId;
import org.home.blackjack.util.ddd.pattern.persistence.PersistenceObject;
import org.home.blackjack.util.ddd.pattern.persistence.PersistenceObjectId;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.hazelcast.core.HazelcastInstance;

@Named
public class HZPlayerStore  implements PlayerStore {
	
	private final Map<StringPersistenceId<PlayerID>, String> jsonMap;
	private final ConcurrentMap<PlayerID, Lock> locks = Maps.newConcurrentMap();

	private JsonPersistenceAssembler<Player> playerStoreAssembler = new JsonPersistenceAssembler<Player>(Player.class, new PlayerGsonProvider());
	
    @Autowired
    public HZPlayerStore(HazelcastInstance hzInstance) {
        jsonMap = hzInstance.getMap("playerHZMap");
    }
	
	@Override
	public JsonPersistenceAssembler<Player> assembler() {
		return playerStoreAssembler;
	}

	@Override
	public JsonPersistenceObject<Player> find(PersistenceObjectId<PlayerID> id) {
		StringPersistenceId<PlayerID> playerID = (StringPersistenceId<PlayerID>) id;
		String json = jsonMap.get(playerID);
		return new JsonPersistenceObject<Player>(json);
	}
	
	@Override
	public void update(PersistenceObject<Player> po) {
		JsonPersistenceObject<Player> mpg = (JsonPersistenceObject<Player>) po;
		jsonMap.put((StringPersistenceId<PlayerID>)mpg.id(), mpg.getJson());
	}

    @Override
    public void create(PersistenceObject<Player> po) {
        JsonPersistenceObject<Player> mpg = (JsonPersistenceObject<Player>) po;
        jsonMap.put((StringPersistenceId<PlayerID>)mpg.id(), mpg.getJson());
    }

    @Override
    public Lock getLockForKey(PlayerID key) {
        locks.putIfAbsent(key, new ReentrantLock());
        return locks.get(key);
    }

	@Override
	public void clear() {
		jsonMap.clear();
		locks.clear();
	}
	//TODO it's not sorted here
    @Override
    public List<PersistenceObject<Player>> findAllSortedByWinNumber() {
        List<PersistenceObject<Player>> result = Lists.newArrayList();
        for(Entry<StringPersistenceId<PlayerID>, String> entry : jsonMap.entrySet()) {
            result.add(new JsonPersistenceObject<Player>(entry.getValue()));
        }
        return result;
    }


}
