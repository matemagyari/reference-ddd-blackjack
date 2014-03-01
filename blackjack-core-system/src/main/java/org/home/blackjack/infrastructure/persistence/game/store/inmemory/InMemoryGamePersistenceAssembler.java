package org.home.blackjack.infrastructure.persistence.game.store.inmemory;

import javax.inject.Named;

import org.home.blackjack.domain.game.Game;
import org.home.blackjack.infrastructure.persistence.game.store.json.GameGsonProvider;
import org.home.blackjack.infrastructure.persistence.shared.GsonBasedAssembler;
import org.home.blackjack.infrastructure.persistence.shared.PersistenceAssembler;
import org.home.blackjack.util.ddd.pattern.ID;

@Named
public class InMemoryGamePersistenceAssembler extends GsonBasedAssembler implements PersistenceAssembler<Game, InMemoryPersistenceGame> {

	public InMemoryGamePersistenceAssembler() {
		super(new GameGsonProvider());
	}
	
	@Override
	public Game toDomain(InMemoryPersistenceGame persistenceObject) {
		return fromJson(persistenceObject.getJson(), Game.class);
	}

	@Override
	public InMemoryPersistenceGame toPersistence(Game domainObject) {
		InMemoryPersistenceGameId id = toPersistence(domainObject.getID());
		String json = toJson(domainObject);
		return new InMemoryPersistenceGame(id,json);
	}

	@Override
	public InMemoryPersistenceGameId toPersistence(ID id) {
		return new InMemoryPersistenceGameId(id.toString());
	}

}
