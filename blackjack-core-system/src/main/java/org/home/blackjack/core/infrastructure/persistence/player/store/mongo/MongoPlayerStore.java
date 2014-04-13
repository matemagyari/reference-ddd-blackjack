package org.home.blackjack.core.infrastructure.persistence.player.store.mongo;

import java.util.List;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.annotation.Resource;
import javax.inject.Named;

import org.home.blackjack.core.domain.player.Player;
import org.home.blackjack.core.domain.shared.PlayerID;
import org.home.blackjack.core.infrastructure.persistence.player.store.PlayerStore;
import org.home.blackjack.core.infrastructure.persistence.shared.core.PersistenceObject;
import org.home.blackjack.core.infrastructure.persistence.shared.core.PersistenceObjectId;
import org.home.blackjack.core.infrastructure.persistence.shared.mongo.MongoStore;

import com.google.common.collect.Maps;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;

@Named
public class MongoPlayerStore extends MongoStore implements PlayerStore {

    @Resource
    private MongoPlayerPersistenceAssembler gameStoreAssembler;

    public MongoPlayerStore() {
        super("blackjack", "player");
    }

    @Override
    public MongoPlayerPersistenceAssembler assembler() {
        return gameStoreAssembler;
    }

    @Override
    public MongoPersistencePlayer find(PersistenceObjectId<PlayerID> id) {
        MongoPersistencePlayerId playerId = (MongoPersistencePlayerId) id;
        DBObject dbObject = super.find(idQuery(playerId));
        return new MongoPersistencePlayer(playerId, dbObject.toString());
    }
    
    @Override
    public void update(PersistenceObject<Player> po) {
        MongoPersistencePlayer mpg = (MongoPersistencePlayer) po;
        DBObject mongoDoc = toDoc(po);
        BasicDBObject query = idQuery(mpg.id());
        super.update(query, mongoDoc);

    }

    @Override
    public void create(PersistenceObject<Player> po) {
        DBObject mongoDoc = toDoc(po);
        super.create(mongoDoc);
    }

    private static DBObject toDoc(PersistenceObject<Player> po) {
        MongoPersistencePlayer mpg = (MongoPersistencePlayer) po;
        return (DBObject) JSON.parse(mpg.getJson());
    }

    private static BasicDBObject idQuery(MongoPersistencePlayerId gameID) {
        BasicDBObject query = new BasicDBObject();
        query.put("id.internal", gameID.getId());
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
