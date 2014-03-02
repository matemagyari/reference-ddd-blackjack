package org.home.blackjack.core.infrastructure.persistence.player.store.inmemory;

import javax.inject.Named;

import org.home.blackjack.core.domain.player.Player;
import org.home.blackjack.core.infrastructure.persistence.player.store.inmemory.json.PlayerGsonProvider;
import org.home.blackjack.core.infrastructure.persistence.shared.GsonBasedAssembler;
import org.home.blackjack.core.infrastructure.persistence.shared.PersistenceAssembler;
import org.home.blackjack.util.ddd.pattern.ID;

@Named
public class InMemoryPlayerPersistenceAssembler extends GsonBasedAssembler implements PersistenceAssembler<Player, InMemoryPersistencePlayer> {

	public InMemoryPlayerPersistenceAssembler() {
		super(new PlayerGsonProvider());
	}
	
	@Override
	public Player toDomain(InMemoryPersistencePlayer persistenceObject) {
		return fromJson(persistenceObject.getJson(), Player.class);
	}

	@Override
	public InMemoryPersistencePlayer toPersistence(Player domainObject) {
		InMemoryPersistencePlayerId id = toPersistence(domainObject.getID());
		String json = toJson(domainObject);
		return new InMemoryPersistencePlayer(id,json);
	}

	@Override
	public InMemoryPersistencePlayerId toPersistence(ID id) {
		return new InMemoryPersistencePlayerId(id.toString());
	}

}
