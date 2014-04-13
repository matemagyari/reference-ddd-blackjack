package org.home.blackjack.core.infrastructure.persistence.game.store.hazelcast;

import javax.inject.Named;

import org.home.blackjack.core.domain.game.Game;
import org.home.blackjack.core.infrastructure.persistence.game.store.json.GameGsonProvider;
import org.home.blackjack.core.infrastructure.persistence.shared.core.PersistenceAssembler;
import org.home.blackjack.core.infrastructure.persistence.shared.gson.GsonBasedAssembler;
import org.home.blackjack.util.ddd.pattern.ID;

@Named
public class HZGamePersistenceAssembler extends GsonBasedAssembler implements PersistenceAssembler<Game, HZPersistenceGame> {

	public HZGamePersistenceAssembler() {
		super(new GameGsonProvider());
	}
	
	@Override
	public Game toDomain(HZPersistenceGame persistenceObject) {
		return fromJson(persistenceObject.getJson(), Game.class);
	}

	@Override
	public HZPersistenceGame toPersistence(Game domainObject) {
		HZPersistenceGameId id = toPersistence(domainObject.getID());
		String json = toJson(domainObject);
		return new HZPersistenceGame(id,json);
	}

	@Override
	public HZPersistenceGameId toPersistence(ID id) {
		return new HZPersistenceGameId(id.toString());
	}
	
}
