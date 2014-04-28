package org.home.blackjack.core.infrastructure.adapters.driving.tablerepository.store.hazelcast;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.inject.Named;

import org.home.blackjack.core.domain.shared.TableID;
import org.home.blackjack.core.domain.table.Table;
import org.home.blackjack.core.infrastructure.adapters.driving.tablerepository.store.TableStore;
import org.home.blackjack.core.infrastructure.adapters.driving.tablerepository.store.serializing.TableGsonProvider;
import org.home.blackjack.core.infrastructure.adapters.util.persistence.json.JsonPersistenceAssembler;
import org.home.blackjack.core.infrastructure.adapters.util.persistence.json.JsonPersistenceObject;
import org.home.blackjack.core.infrastructure.adapters.util.persistence.json.StringPersistenceId;
import org.home.blackjack.util.ddd.pattern.infrastructure.persistence.PersistenceObject;
import org.home.blackjack.util.ddd.pattern.infrastructure.persistence.PersistenceObjectId;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.hazelcast.core.HazelcastInstance;

@Named
public class HZTableStore  implements TableStore {
	
	private final Map<StringPersistenceId<TableID>, String> jsonMap;
	private final ConcurrentMap<TableID, Lock> locks = Maps.newConcurrentMap();

	private JsonPersistenceAssembler<Table> tableStoreAssembler = new JsonPersistenceAssembler<Table>(Table.class, new TableGsonProvider());
	
    @Autowired
    public HZTableStore(HazelcastInstance hzInstance) {
        jsonMap = hzInstance.getMap("tableHZMap");
    }
	
	@Override
	public JsonPersistenceAssembler<Table> assembler() {
		return tableStoreAssembler;
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
        for(Entry<StringPersistenceId<TableID>, String> entry : jsonMap.entrySet()) {
            result.add( new JsonPersistenceObject<Table>(entry.getValue()));
        }
        return result;
	}

	@Override
	public boolean isEmpty() {
		return jsonMap.isEmpty();
	}

}
