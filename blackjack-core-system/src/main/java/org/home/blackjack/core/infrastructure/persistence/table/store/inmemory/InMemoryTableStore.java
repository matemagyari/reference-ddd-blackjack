package org.home.blackjack.core.infrastructure.persistence.table.store.inmemory;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.inject.Inject;
import javax.inject.Named;

import org.home.blackjack.core.domain.shared.TableID;
import org.home.blackjack.core.domain.table.Table;
import org.home.blackjack.core.infrastructure.persistence.shared.core.PersistenceAssembler;
import org.home.blackjack.core.infrastructure.persistence.shared.core.PersistenceObject;
import org.home.blackjack.core.infrastructure.persistence.shared.core.PersistenceObjectId;
import org.home.blackjack.core.infrastructure.persistence.table.store.TableStore;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

@Named
public class InMemoryTableStore implements TableStore {
	
	private final Map<InMemoryPersistenceTableId, String> jsonMap = Maps.newHashMap();
	private final ConcurrentMap<TableID, Lock> locks = Maps.newConcurrentMap();

	private final InMemoryTablePersistenceAssembler playerStoreAssembler;
	
	@Inject
	public InMemoryTableStore(InMemoryTablePersistenceAssembler playerStoreAssembler) {
		this.playerStoreAssembler = playerStoreAssembler;
	}
	
	@Override
	public InMemoryTablePersistenceAssembler assembler() {
		return playerStoreAssembler;
	}

	@Override
	public InMemoryPersistenceTable find(PersistenceObjectId<TableID> id) {
		InMemoryPersistenceTableId playerID = (InMemoryPersistenceTableId) id;
		String json = jsonMap.get(playerID);
		return new InMemoryPersistenceTable(playerID, json);
	}
	

	@Override
	public void update(PersistenceObject<Table> po) {
		InMemoryPersistenceTable mpg = (InMemoryPersistenceTable) po;
		jsonMap.put(mpg.id(), mpg.getJson());
	}

	@Override
	public void create(PersistenceObject<Table> po) {
		InMemoryPersistenceTable mpg = (InMemoryPersistenceTable) po;
		jsonMap.put(mpg.id(), mpg.getJson());
	}
	
	@Override
	public void clear() {
		jsonMap.clear();
	}

    @Override
    public List<PersistenceObject<Table>> findAll() {
        List<PersistenceObject<Table>> result = Lists.newArrayList();
        for(Entry<InMemoryPersistenceTableId, String> entry : jsonMap.entrySet()) {
            result.add( new InMemoryPersistenceTable(entry.getKey(), entry.getValue()));
        }
        return result;
    }

    @Override
    public Lock getLockForKey(TableID key) {
        locks.putIfAbsent(key, new ReentrantLock());
        return locks.get(key);
    }

	@Override
	public boolean isEmpty() {
		return jsonMap.isEmpty();
	}
}
