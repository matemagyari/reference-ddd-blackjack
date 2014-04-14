package org.home.blackjack.core.infrastructure.persistence.table.store.hazelcast;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.annotation.Resource;

import org.home.blackjack.core.domain.shared.TableID;
import org.home.blackjack.core.domain.table.Table;
import org.home.blackjack.core.infrastructure.persistence.shared.core.PersistenceObject;
import org.home.blackjack.core.infrastructure.persistence.shared.core.PersistenceObjectId;
import org.home.blackjack.core.infrastructure.persistence.table.store.TableStore;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.hazelcast.core.HazelcastInstance;

public class HZTableStore  implements TableStore {
	
	private final Map<HZPersistenceTableId, String> jsonMap;
	private final ConcurrentMap<TableID, Lock> locks = Maps.newConcurrentMap();

	@Resource
	private HZTablePersistenceAssembler playerStoreAssembler;
	
    @Autowired
    public HZTableStore(HazelcastInstance hzInstance) {
        jsonMap = hzInstance.getMap("tableHZMap");
    }
	
	@Override
	public HZTablePersistenceAssembler assembler() {
		return playerStoreAssembler;
	}

	@Override
	public HZPersistenceTable find(PersistenceObjectId<TableID> id) {
		HZPersistenceTableId playerID = (HZPersistenceTableId) id;
		String json = jsonMap.get(playerID);
		return new HZPersistenceTable(playerID, json);
	}
	
	@Override
	public void update(PersistenceObject<Table> po) {
		HZPersistenceTable mpg = (HZPersistenceTable) po;
		jsonMap.put(mpg.id(), mpg.getJson());
	}

    @Override
    public void create(PersistenceObject<Table> po) {
        HZPersistenceTable mpg = (HZPersistenceTable) po;
        jsonMap.put(mpg.id(), mpg.getJson());
    }

    @Override
    public Lock getLockForKey(TableID key) {
        locks.putIfAbsent(key, new ReentrantLock());
        return locks.get(key);
    }

	@Override
	public void clear() {
		jsonMap.clear();
		locks.clear();
	}

	@Override
	public List<PersistenceObject<Table>> findAll() {
        List<PersistenceObject<Table>> result = Lists.newArrayList();
        for(Entry<HZPersistenceTableId, String> entry : jsonMap.entrySet()) {
            result.add( new HZPersistenceTable(entry.getKey(), entry.getValue()));
        }
        return result;
	}

	@Override
	public boolean isEmpty() {
		return jsonMap.isEmpty();
	}

}
