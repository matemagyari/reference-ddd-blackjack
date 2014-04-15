package org.home.blackjack.core.infrastructure.persistence.game.store.mongo;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.inject.Named;

import org.home.blackjack.core.domain.game.Game;
import org.home.blackjack.core.domain.game.core.GameID;
import org.home.blackjack.core.domain.shared.TableID;
import org.home.blackjack.core.infrastructure.persistence.game.store.GameStore;
import org.home.blackjack.core.infrastructure.persistence.game.store.json.GameGsonProvider;
import org.home.blackjack.core.infrastructure.persistence.shared.core.PersistenceObject;
import org.home.blackjack.core.infrastructure.persistence.shared.core.PersistenceObjectId;
import org.home.blackjack.core.infrastructure.persistence.shared.json.JsonPersistenceAssembler;
import org.home.blackjack.core.infrastructure.persistence.shared.json.JsonPersistenceObject;
import org.home.blackjack.core.infrastructure.persistence.shared.json.StringPersistenceId;
import org.home.blackjack.core.infrastructure.persistence.shared.mongo.MongoStore;

import com.google.common.collect.Maps;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;

@Named
public class MongoGameStore extends MongoStore implements GameStore {

    private JsonPersistenceAssembler<Game> gameStoreAssembler = new JsonPersistenceAssembler<Game>(Game.class, new GameGsonProvider());

    public MongoGameStore() {
        super("blackjack", "game");
    }

    @Override
    public JsonPersistenceAssembler<Game> assembler() {
        return gameStoreAssembler;
    }

    @Override
    public JsonPersistenceObject<Game> find(PersistenceObjectId<GameID> id) {
    	StringPersistenceId<GameID> gameID = (StringPersistenceId<GameID>) id;
        DBObject dbObject = super.find(idQuery(gameID));
        return new JsonPersistenceObject<Game>(dbObject.toString());
    }
    
    @Override
    public JsonPersistenceObject<Game> find(TableID id) {
        BasicDBObject query = new BasicDBObject();
        query.put("tableId", id.toString());
        DBObject dbObject = super.find(query);
        return new JsonPersistenceObject<Game>(dbObject.toString());
    }

    @Override
    public void update(PersistenceObject<Game> po) {
    	JsonPersistenceObject<Game> mpg = (JsonPersistenceObject<Game>) po;
        DBObject mongoDoc = toDoc(po);
        BasicDBObject query = idQuery((StringPersistenceId<GameID>) mpg.id());
        super.update(query, mongoDoc);

    }

    @Override
    public void create(PersistenceObject<Game> po) {
        DBObject mongoDoc = toDoc(po);
        super.create(mongoDoc);
    }

    private static DBObject toDoc(PersistenceObject<Game> po) {
    	JsonPersistenceObject<Game> mpg = (JsonPersistenceObject<Game>) po;
        return (DBObject) JSON.parse(mpg.getJson());
    }

    private static BasicDBObject idQuery(StringPersistenceId<GameID> gameID) {
        BasicDBObject query = new BasicDBObject();
        query.put("id.internal", gameID.getId());
        return query;
    }
    
    
    protected final ConcurrentMap<GameID, Lock> locks = Maps.newConcurrentMap();
    @Override
    public Lock getLockForKey(GameID key) {
        //TODO implement it properly
        locks.putIfAbsent(key, new ReentrantLock());
        return locks.get(key);
    }

}
