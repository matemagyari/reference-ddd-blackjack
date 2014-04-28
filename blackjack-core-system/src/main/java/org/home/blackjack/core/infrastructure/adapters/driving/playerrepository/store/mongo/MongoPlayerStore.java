package org.home.blackjack.core.infrastructure.adapters.driving.playerrepository.store.mongo;

import java.util.List;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.inject.Named;

import org.home.blackjack.core.domain.player.Player;
import org.home.blackjack.core.domain.shared.PlayerID;
import org.home.blackjack.core.infrastructure.adapters.driving.playerrepository.store.PlayerStore;
import org.home.blackjack.core.infrastructure.adapters.driving.playerrepository.store.serializing.PlayerGsonProvider;
import org.home.blackjack.core.infrastructure.adapters.util.persistence.json.JsonPersistenceAssembler;
import org.home.blackjack.core.infrastructure.adapters.util.persistence.json.JsonPersistenceObject;
import org.home.blackjack.core.infrastructure.adapters.util.persistence.json.StringPersistenceId;
import org.home.blackjack.core.infrastructure.adapters.util.persistence.mongo.MongoStore;
import org.home.blackjack.util.ddd.pattern.infrastructure.persistence.PersistenceObject;
import org.home.blackjack.util.ddd.pattern.infrastructure.persistence.PersistenceObjectId;

import com.google.common.collect.Maps;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;

@Named
public class MongoPlayerStore extends MongoStore implements PlayerStore {

    private JsonPersistenceAssembler<Player> playerStoreAssembler = new JsonPersistenceAssembler<Player>(Player.class, new PlayerGsonProvider());

    public MongoPlayerStore() {
        super("blackjack", "player");
    }

    @Override
    public JsonPersistenceAssembler<Player> assembler() {
        return playerStoreAssembler;
    }

    @Override
    public JsonPersistenceObject<Player> find(PersistenceObjectId<PlayerID> id) {
        StringPersistenceId<PlayerID> playerId = (StringPersistenceId<PlayerID>) id;
        DBObject dbObject = super.find(idQuery(playerId));
        return new JsonPersistenceObject<Player>(dbObject.toString());
    }
    
    @Override
    public void update(PersistenceObject<Player> po) {
        JsonPersistenceObject<Player> mpg = (JsonPersistenceObject<Player>) po;
        DBObject mongoDoc = toDoc(po);
        BasicDBObject query = idQuery((StringPersistenceId<PlayerID>)mpg.id());
        super.update(query, mongoDoc);

    }

    @Override
    public void create(PersistenceObject<Player> po) {
        DBObject mongoDoc = toDoc(po);
        super.create(mongoDoc);
    }

    private static DBObject toDoc(PersistenceObject<Player> po) {
        JsonPersistenceObject<Player> mpg = (JsonPersistenceObject<Player>) po;
        return (DBObject) JSON.parse(mpg.getJson());
    }

    private static BasicDBObject idQuery(StringPersistenceId<PlayerID> playerId) {
        BasicDBObject query = new BasicDBObject();
        query.put("id.internal", playerId.getId());
        return query;
    }

    @Override
    public List<PersistenceObject<Player>> findAllSortedByWinNumber() {
        //TODO implement
        throw new RuntimeException();
    }
    
    protected final ConcurrentMap<PlayerID, Lock> locks = Maps.newConcurrentMap();
    @Override
    public Lock getLockForKey(PlayerID key) {
        //TODO implement it properly
        locks.putIfAbsent(key, new ReentrantLock());
        return locks.get(key);
    }

}
