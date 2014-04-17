package org.home.blackjack.core.infrastructure.persistence.table.store.inmemory;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.inject.Named;

import org.home.blackjack.core.domain.shared.TableID;
import org.home.blackjack.core.domain.table.Table;
import org.home.blackjack.core.infrastructure.persistence.shared.json.JsonPersistenceAssembler;
import org.home.blackjack.core.infrastructure.persistence.shared.json.JsonPersistenceObject;
import org.home.blackjack.core.infrastructure.persistence.shared.json.StringPersistenceId;
import org.home.blackjack.core.infrastructure.persistence.table.store.TableStore;
import org.home.blackjack.core.infrastructure.persistence.table.store.json.TableGsonProvider;
import org.home.blackjack.util.ddd.pattern.persistence.PersistenceObject;
import org.home.blackjack.util.ddd.pattern.persistence.PersistenceObjectId;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

@Named
public class InMemoryTableStore implements TableStore {
	
	private final Map<StringPersistenceId<TableID>, String> jsonMap = Maps.newHashMap();
	private final ConcurrentMap<TableID, Lock> locks = Maps.newConcurrentMap();

	 private JsonPersistenceAssembler<Table> playerStoreAssembler = new JsonPersistenceAssembler<Table>(Table.class, new TableGsonProvider());
	
	@Override
	public JsonPersistenceAssembler<Table> assembler() {
		return playerStoreAssembler;
	}

	@Override
	public JsonPersistenceObject<Table> find(PersistenceObjectId<TableID> id) {
		StringPersistenceId<TableID> playerID = (StringPersistenceId<TableID>) id;
		String json = jsonMap.get(playerID);
		return new JsonPersistenceObject<Table>(json);
	}
	

	@Override
	public void update(PersistenceObject<Table> po) {
		JsonPersistenceObject<Table> mpg = (JsonPersistenceObject<Table>) po;
		jsonMap.put((StringPersistenceId<TableID>)mpg.id(), mpg.getJson());
	}

	@Override
	public void create(PersistenceObject<Table> po) {
		JsonPersistenceObject<Table> mpg = (JsonPersistenceObject<Table>) po;
		jsonMap.put((StringPersistenceId<TableID>)mpg.id(), mpg.getJson());
	}
	
	@Override
	public void clear() {
		jsonMap.clear();
	}

    @Override
    public List<PersistenceObject<Table>> findAll() {
        List<PersistenceObject<Table>> result = Lists.newArrayList();
        for(Entry<StringPersistenceId<TableID>, String> entry : jsonMap.entrySet()) {
            result.add( new JsonPersistenceObject<Table>(entry.getValue()));
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
