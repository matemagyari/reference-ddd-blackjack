package org.home.blackjack.core.infrastructure.persistence.player.store.hazelcast;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.annotation.Resource;

import org.home.blackjack.core.domain.player.Player;
import org.home.blackjack.core.domain.shared.PlayerID;
import org.home.blackjack.core.infrastructure.persistence.player.store.PlayerStore;
import org.home.blackjack.core.infrastructure.persistence.shared.core.PersistenceObject;
import org.home.blackjack.core.infrastructure.persistence.shared.core.PersistenceObjectId;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class HZPlayerStore  implements PlayerStore {
	
	private final Map<HZPersistencePlayerId, String> jsonMap = Maps.newHashMap();
	private final ConcurrentMap<PlayerID, Lock> locks = Maps.newConcurrentMap();

	@Resource
	private HZPlayerPersistenceAssembler playerStoreAssembler;
	
	@Override
	public HZPlayerPersistenceAssembler assembler() {
		return playerStoreAssembler;
	}

	@Override
	public HZPersistencePlayer find(PersistenceObjectId<PlayerID> id) {
		HZPersistencePlayerId playerID = (HZPersistencePlayerId) id;
		String json = jsonMap.get(playerID);
		return new HZPersistencePlayer(playerID, json);
	}
	
	@Override
	public void update(PersistenceObject<Player> po) {
		HZPersistencePlayer mpg = (HZPersistencePlayer) po;
		jsonMap.put(mpg.id(), mpg.getJson());
	}

    @Override
    public void create(PersistenceObject<Player> po) {
        HZPersistencePlayer mpg = (HZPersistencePlayer) po;
        jsonMap.put(mpg.id(), mpg.getJson());
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
        for(Entry<HZPersistencePlayerId, String> entry : jsonMap.entrySet()) {
            result.add(new HZPersistencePlayer(entry.getKey(), entry.getValue()));
        }
        return result;
    }


}
