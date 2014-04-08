package org.home.blackjack.core.infrastructure.persistence.shared;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.home.blackjack.core.domain.game.core.GameID;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.ReadPreference;
import com.mongodb.WriteConcern;

public abstract class MongoStore {
    
    @Resource
    private Mongo mongo;

    private DB db;

    private final String dbName;
    private final String collectionName;
    
    protected ReadPreference readPreference = ReadPreference.primary();
    protected WriteConcern writeConcern = WriteConcern.SAFE;
    
    public MongoStore(String dbName, String collectionName) {
        this.dbName = dbName;
        this.collectionName = collectionName;
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

    
    protected DBObject find(BasicDBObject query) {
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
    
    
    protected void update(BasicDBObject query, DBObject mongoDoc) {
        collection().update(query, mongoDoc, false, false, writeConcern);

    }
    
    protected void create(DBObject mongoDoc) {
        collection().save(mongoDoc, writeConcern);
    }

    
    public Lock getLockForKey(GameID key) {
        //TODO implement it properly
        return new ReentrantLock();
    }

    
    public void clear() {
        collection().drop();
    }
    

    
    private DBCollection collection() {
        return db.getCollection(collectionName);
    }
}
