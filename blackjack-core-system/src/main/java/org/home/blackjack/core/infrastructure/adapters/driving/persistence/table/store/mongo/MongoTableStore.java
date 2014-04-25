package org.home.blackjack.core.infrastructure.adapters.driving.persistence.table.store.mongo;

import java.util.List;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.inject.Named;

import org.home.blackjack.core.domain.shared.TableID;
import org.home.blackjack.core.domain.table.Table;
import org.home.blackjack.core.infrastructure.adapters.driving.persistence.shared.json.JsonPersistenceAssembler;
import org.home.blackjack.core.infrastructure.adapters.driving.persistence.shared.json.JsonPersistenceObject;
import org.home.blackjack.core.infrastructure.adapters.driving.persistence.shared.json.StringPersistenceId;
import org.home.blackjack.core.infrastructure.adapters.driving.persistence.shared.mongo.MongoStore;
import org.home.blackjack.core.infrastructure.adapters.driving.persistence.table.store.TableStore;
import org.home.blackjack.core.infrastructure.adapters.driving.persistence.table.store.serializing.TableGsonProvider;
import org.home.blackjack.util.ddd.pattern.infrastructure.persistence.PersistenceObject;
import org.home.blackjack.util.ddd.pattern.infrastructure.persistence.PersistenceObjectId;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;

@Named
public class MongoTableStore extends MongoStore implements TableStore {

    private JsonPersistenceAssembler<Table> tableStoreAssembler = new JsonPersistenceAssembler<Table>(Table.class, new TableGsonProvider());

    public MongoTableStore() {
        super("blackjack", "table");
    }

    @Override
    public JsonPersistenceAssembler<Table> assembler() {
        return tableStoreAssembler;
    }

    @Override
    public JsonPersistenceObject<Table> find(PersistenceObjectId<TableID> id) {
        StringPersistenceId<TableID> playerId = (StringPersistenceId<TableID>) id;
        DBObject dbObject = super.find(idQuery(playerId));
        return new JsonPersistenceObject<Table>(dbObject.toString());
    }
    
    @Override
    public void update(PersistenceObject<Table> po) {
        JsonPersistenceObject<Table> mpg = (JsonPersistenceObject<Table>) po;
        DBObject mongoDoc = toDoc(po);
        BasicDBObject query = idQuery((StringPersistenceId<TableID>)mpg.id());
        super.update(query, mongoDoc);

    }

    @Override
    public void create(PersistenceObject<Table> po) {
        DBObject mongoDoc = toDoc(po);
        super.create(mongoDoc);
    }

    private static DBObject toDoc(PersistenceObject<Table> po) {
        JsonPersistenceObject<Table> mpg = (JsonPersistenceObject<Table>) po;
        return (DBObject) JSON.parse(mpg.getJson());
    }

    private static BasicDBObject idQuery(StringPersistenceId<TableID> gameID) {
        BasicDBObject query = new BasicDBObject();
        query.put("id.internal", gameID.getId());
        return query;
    }

    @Override
    public List<PersistenceObject<Table>> findAll() {
        List<PersistenceObject<Table>> result = Lists.newArrayList();
        for(DBObject dbObject : super.basicfindAll()) {
            result.add(new JsonPersistenceObject<Table>(dbObject.toString()));
        }
        return result;
    }
    
	@Override
	public boolean isEmpty() {
		return super.isEmpty();
	}
    
    
    protected final ConcurrentMap<TableID, Lock> locks = Maps.newConcurrentMap();
    @Override
    public Lock getLockForKey(TableID key) {
        //TODO implement it properly
        locks.putIfAbsent(key, new ReentrantLock());
        return locks.get(key);
    }
}
