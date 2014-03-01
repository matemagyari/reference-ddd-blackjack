package org.home.blackjack.infrastructure.persistence.player.store.inmemory;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.home.blackjack.domain.player.Player;
import org.home.blackjack.domain.shared.PlayerID;
import org.home.blackjack.infrastructure.persistence.player.store.PlayerStore;
import org.home.blackjack.infrastructure.persistence.shared.PersistenceObject;
import org.home.blackjack.infrastructure.persistence.shared.PersistenceObjectId;

import com.google.common.collect.Maps;

@Named
public class InMemoryPlayerStore implements PlayerStore {
	
	private final Map<InMemoryPersistencePlayerId, String> jsonMap = Maps.newHashMap();

	private final InMemoryPlayerPersistenceAssembler playerStoreAssembler;
	
	@Inject
	public InMemoryPlayerStore(InMemoryPlayerPersistenceAssembler playerStoreAssembler) {
		this.playerStoreAssembler = playerStoreAssembler;
	}
	
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
	
	@Override
	public void clear() {
		jsonMap.clear();
	}
}
