package org.home.blackjack.core.infrastructure.persistence.game.store.mongo;

import javax.annotation.Resource;
import javax.inject.Named;

import org.home.blackjack.core.domain.game.Game;
import org.home.blackjack.core.domain.game.core.GameID;
import org.home.blackjack.core.domain.shared.TableID;
import org.home.blackjack.core.infrastructure.persistence.game.store.GameStore;
import org.home.blackjack.core.infrastructure.persistence.shared.MongoStore;
import org.home.blackjack.core.infrastructure.persistence.shared.PersistenceObject;
import org.home.blackjack.core.infrastructure.persistence.shared.PersistenceObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;

@Named
public class MongoGameStore extends MongoStore implements GameStore {

    @Resource
    private MongoGamePersistenceAssembler gameStoreAssembler;

    public MongoGameStore() {
        super("blackjack", "game");
    }

    @Override
    public MongoGamePersistenceAssembler assembler() {
        return gameStoreAssembler;
    }

    @Override
    public MongoPersistenceGame find(PersistenceObjectId<GameID> id) {
        MongoPersistenceGameId gameID = (MongoPersistenceGameId) id;
        DBObject dbObject = super.find(idQuery(gameID));
        return new MongoPersistenceGame(gameID, dbObject.toString());
    }
    
    @Override
    public MongoPersistenceGame find(TableID id) {
        BasicDBObject query = new BasicDBObject();
        query.put("tableId", id.toString());
        DBObject dbObject = super.find(query);
        return new MongoPersistenceGame(dbObject.toString());
    }

    @Override
    public void update(PersistenceObject<Game> po) {
        MongoPersistenceGame mpg = (MongoPersistenceGame) po;
        DBObject mongoDoc = toDoc(po);
        BasicDBObject query = idQuery(mpg.id());
        super.update(query, mongoDoc);

    }

    @Override
    public void create(PersistenceObject<Game> po) {
        DBObject mongoDoc = toDoc(po);
        super.create(mongoDoc);
    }

    private static DBObject toDoc(PersistenceObject<Game> po) {
        MongoPersistenceGame mpg = (MongoPersistenceGame) po;
        return (DBObject) JSON.parse(mpg.getJson());
    }

    private static BasicDBObject idQuery(MongoPersistenceGameId gameID) {
        BasicDBObject query = new BasicDBObject();
        query.put("id.internal", gameID.getId());
        return query;
    }

}
