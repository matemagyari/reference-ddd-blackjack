package org.home.blackjack.infrastructure.persistence.player.store.inmemory;

import org.home.blackjack.domain.player.Player;
import org.home.blackjack.infrastructure.persistence.shared.PersistenceObject;

public class InMemoryPersistencePlayer implements PersistenceObject<Player> {
	
	private final InMemoryPersistencePlayerId id;
	private final String json;
	
	public InMemoryPersistencePlayer(InMemoryPersistencePlayerId id, String json) {
		this.id = id;
		this.json = json;
	}
	
	public String getJson() {
		return json;
	}

	@Override
	public InMemoryPersistencePlayerId id() {
		return id;
	}

}
