package org.home.blackjack.core.infrastructure.persistence.player.store.hazelcast;

import javax.inject.Named;

import org.home.blackjack.core.domain.player.Player;
import org.home.blackjack.core.infrastructure.persistence.player.store.json.PlayerGsonProvider;
import org.home.blackjack.core.infrastructure.persistence.shared.core.PersistenceAssembler;
import org.home.blackjack.core.infrastructure.persistence.shared.gson.GsonBasedAssembler;
import org.home.blackjack.util.ddd.pattern.ID;

@Named
public class HZPlayerPersistenceAssembler extends GsonBasedAssembler implements PersistenceAssembler<Player, HZPersistencePlayer> {

	public HZPlayerPersistenceAssembler() {
		super(new PlayerGsonProvider());
	}
	
	@Override
	public Player toDomain(HZPersistencePlayer persistenceObject) {
		return fromJson(persistenceObject.getJson(), Player.class);
	}

	@Override
	public HZPersistencePlayer toPersistence(Player domainObject) {
		HZPersistencePlayerId id = toPersistence(domainObject.getID());
		String json = toJson(domainObject);
		return new HZPersistencePlayer(id,json);
	}

	@Override
	public HZPersistencePlayerId toPersistence(ID id) {
		return new HZPersistencePlayerId(id.toString());
	}
	
}
