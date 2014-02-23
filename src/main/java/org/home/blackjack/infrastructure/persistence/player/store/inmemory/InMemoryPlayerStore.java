package org.home.blackjack.infrastructure.persistence.player.store.inmemory;

import java.util.Map;

import org.home.blackjack.domain.player.Player;
import org.home.blackjack.domain.shared.PlayerID;
import org.home.blackjack.infrastructure.persistence.player.store.PlayerStore;
import org.home.blackjack.infrastructure.persistence.shared.PersistenceObject;
import org.home.blackjack.infrastructure.persistence.shared.PersistenceObjectId;

import com.google.common.collect.Maps;

public class InMemoryPlayerStore implements PlayerStore {
	
	private final Map<InMemoryPersistencePlayerId, String> jsonMap = Maps.newHashMap();

	private InMemoryPlayerPersistenceAssembler playerStoreAssembler;
	
	@Override
	public InMemoryPlayerPersistenceAssembler assembler() {
		return playerStoreAssembler;
	}

	@Override
	public InMemoryPersistencePlayer find(PersistenceObjectId<PlayerID> id) {
		InMemoryPersistencePlayerId playerID = (InMemoryPersistencePlayerId) id;
		String json = jsonMap.get(playerID);
		return new InMemoryPersistencePlayer(playerID, json);
	}

	@Override
	public void update(PersistenceObject<Player> po) {
		InMemoryPersistencePlayer mpg = (InMemoryPersistencePlayer) po;
		jsonMap.put(mpg.id(), mpg.getJson());
	}

	@Override
	public void create(PersistenceObject<Player> po) {
		InMemoryPersistencePlayer mpg = (InMemoryPersistencePlayer) po;
		jsonMap.put(mpg.id(), mpg.getJson());
	}
}
