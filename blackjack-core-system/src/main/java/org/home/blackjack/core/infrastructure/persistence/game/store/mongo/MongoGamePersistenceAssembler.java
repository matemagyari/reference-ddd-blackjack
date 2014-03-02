package org.home.blackjack.core.infrastructure.persistence.game.store.mongo;

import org.home.blackjack.core.domain.game.Game;
import org.home.blackjack.core.infrastructure.persistence.game.store.json.GameGsonProvider;
import org.home.blackjack.core.infrastructure.persistence.shared.GsonBasedAssembler;
import org.home.blackjack.core.infrastructure.persistence.shared.PersistenceAssembler;
import org.home.blackjack.util.ddd.pattern.ID;

public class MongoGamePersistenceAssembler extends GsonBasedAssembler implements PersistenceAssembler<Game, MongoPersistenceGame> {

	public MongoGamePersistenceAssembler() {
		super(new GameGsonProvider());
	}
	
	@Override
	public Game toDomain(MongoPersistenceGame persistenceObject) {
		return fromJson(persistenceObject.getJson(), Game.class);
	}

	@Override
	public MongoPersistenceGame toPersistence(Game domainObject) {
		MongoPersistenceGameId id = toPersistence(domainObject.getID());
		String json = toJson(domainObject);
		return new MongoPersistenceGame(id,json);
	}

	@Override
	public MongoPersistenceGameId toPersistence(ID id) {
		return new MongoPersistenceGameId(id.toString());
	}
	
}
