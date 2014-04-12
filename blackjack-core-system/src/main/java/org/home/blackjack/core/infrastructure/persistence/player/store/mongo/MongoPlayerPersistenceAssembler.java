package org.home.blackjack.core.infrastructure.persistence.player.store.mongo;

import javax.inject.Named;

import org.home.blackjack.core.domain.player.Player;
import org.home.blackjack.core.infrastructure.persistence.player.store.json.PlayerGsonProvider;
import org.home.blackjack.core.infrastructure.persistence.shared.GsonBasedAssembler;
import org.home.blackjack.core.infrastructure.persistence.shared.PersistenceAssembler;
import org.home.blackjack.util.ddd.pattern.ID;

@Named
public class MongoPlayerPersistenceAssembler extends GsonBasedAssembler implements PersistenceAssembler<Player, MongoPersistencePlayer> {

	public MongoPlayerPersistenceAssembler() {
		super(new PlayerGsonProvider());
	}
	
	@Override
	public Player toDomain(MongoPersistencePlayer persistenceObject) {
		return fromJson(persistenceObject.getJson(), Player.class);
	}

	@Override
	public MongoPersistencePlayer toPersistence(Player domainObject) {
		MongoPersistencePlayerId id = toPersistence(domainObject.getID());
		String json = toJson(domainObject);
		return new MongoPersistencePlayer(id,json);
	}

	@Override
	public MongoPersistencePlayerId toPersistence(ID id) {
		return new MongoPersistencePlayerId(id.toString());
	}
	
}
