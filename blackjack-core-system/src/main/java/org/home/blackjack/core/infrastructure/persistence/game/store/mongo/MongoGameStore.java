package org.home.blackjack.core.infrastructure.persistence.game.store.mongo;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.inject.Named;

import org.home.blackjack.core.domain.game.Game;
import org.home.blackjack.core.domain.game.core.GameID;
import org.home.blackjack.core.domain.shared.TableID;
import org.home.blackjack.core.infrastructure.persistence.game.store.GameStore;
import org.home.blackjack.core.infrastructure.persistence.shared.PersistenceObject;
import org.home.blackjack.core.infrastructure.persistence.shared.PersistenceObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.ReadPreference;
import com.mongodb.WriteConcern;
import com.mongodb.util.JSON;

@Named
public class MongoGameStore implements GameStore {

    @Resource
    private MongoGamePersistenceAssembler gameStoreAssembler;

    @Resource
    private Mongo mongo;

    private DB db;

    private final String dbName;
    private final String collectionName;
    
    protected ReadPreference readPreference = ReadPreference.primary();
    protected WriteConcern writeConcern = WriteConcern.SAFE;
    
    public MongoGameStore() {
        this.dbName = "blackjack";
        this.collectionName = "game";
    }
    
    @PostConstruct
    void init() {
        if (dbName == null || dbName.equals(""))
            throw new IllegalStateException("dbName cannot be null or empty");
        db = mongo.getDB(dbName);
        initCollections(db);
    }

    private void initCollections(DB db) {
        if (!db.collectionExists(collectionName)) {
            db.createCollection(collectionName, new BasicDBObject());
        }
    }


    @Override
    public MongoGamePersistenceAssembler assembler() {
        return gameStoreAssembler;
    }

    @Override
    public MongoPersistenceGame find(PersistenceObjectId<GameID> id) {
        MongoPersistenceGameId gameID = (MongoPersistenceGameId) id;
        DBObject dbObject = find(gameID);
        return new MongoPersistenceGame(gameID, dbObject.toString());
    }
    
    @Override
    public MongoPersistenceGame find(TableID id) {
        BasicDBObject query = new BasicDBObject();
        query.put("tableId", id.toString());
        DBObject dbObject = find(query);
        return new MongoPersistenceGame(dbObject.toString());
    }

    @Override
    public void update(PersistenceObject<Game> po) {
        MongoPersistenceGame mpg = (MongoPersistenceGame) po;
        DBObject mongoDoc = (DBObject) JSON.parse(mpg.getJson());
        BasicDBObject query = idQuery(mpg.id());
        collection().update(query, mongoDoc, false, false, writeConcern);

    }

    @Override
    public void create(PersistenceObject<Game> po) {
        MongoPersistenceGame mpg = (MongoPersistenceGame) po;
        DBObject mongoDoc = (DBObject) JSON.parse(mpg.getJson());
        collection().save(mongoDoc, writeConcern);
    }

    @Override
    public Lock getLockForKey(GameID key) {
        //TODO implement it properly
        return new ReentrantLock();
    }

    @Override
    public void clear() {
        collection().drop();
    }
    
    private DBObject find(MongoPersistenceGameId gameID) {
        return find(idQuery(gameID));
    }

    private BasicDBObject idQuery(MongoPersistenceGameId gameID) {
        BasicDBObject query = new BasicDBObject();
        query.put("id.internal", gameID.getId());
        return query;
    }

    private DBObject find(BasicDBObject query) {
        DBCursor cursor = null;
        try {
            cursor = collection().find(query).slaveOk();
            if (cursor.hasNext()) {
                return cursor.next();
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return null;
    }
    
    private DBCollection collection() {
        return db.getCollection(collectionName);
    }

}
