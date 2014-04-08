package org.home.blackjack.core.infrastructure.persistence.table.store.mongo;

import javax.annotation.Resource;
import javax.inject.Named;

import org.home.blackjack.core.domain.shared.TableID;
import org.home.blackjack.core.domain.table.Table;
import org.home.blackjack.core.infrastructure.persistence.shared.MongoStore;
import org.home.blackjack.core.infrastructure.persistence.shared.PersistenceObject;
import org.home.blackjack.core.infrastructure.persistence.shared.PersistenceObjectId;
import org.home.blackjack.core.infrastructure.persistence.table.store.TableStore;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;

@Named
public class MongoTableStore extends MongoStore implements TableStore {

    @Resource
    private MongoTablePersistenceAssembler tableStoreAssembler;

    public MongoTableStore() {
        super("blackjack", "table");
    }

    @Override
    public MongoTablePersistenceAssembler assembler() {
        return tableStoreAssembler;
    }

    @Override
    public MongoPersistenceTable find(PersistenceObjectId<TableID> id) {
        MongoPersistenceTableId playerId = (MongoPersistenceTableId) id;
        DBObject dbObject = super.find(idQuery(playerId));
        return new MongoPersistenceTable(playerId, dbObject.toString());
    }
    
    @Override
    public void update(PersistenceObject<Table> po) {
        MongoPersistenceTable mpg = (MongoPersistenceTable) po;
        DBObject mongoDoc = toDoc(po);
        BasicDBObject query = idQuery(mpg.id());
        super.update(query, mongoDoc);

    }

    @Override
    public void create(PersistenceObject<Table> po) {
        DBObject mongoDoc = toDoc(po);
        super.create(mongoDoc);
    }

    private static DBObject toDoc(PersistenceObject<Table> po) {
        MongoPersistenceTable mpg = (MongoPersistenceTable) po;
        return (DBObject) JSON.parse(mpg.getJson());
    }

    private static BasicDBObject idQuery(MongoPersistenceTableId gameID) {
        BasicDBObject query = new BasicDBObject();
        query.put("id.internal", gameID.getId());
        return query;
    }


}
