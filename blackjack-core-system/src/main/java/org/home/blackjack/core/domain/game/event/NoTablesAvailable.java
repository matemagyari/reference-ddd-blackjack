package org.home.blackjack.core.domain.game.event;

import org.home.blackjack.core.domain.shared.PlayerID;
import org.home.blackjack.util.ddd.pattern.events.DomainEvent;

public class NoTablesAvailable implements DomainEvent {

	private final PlayerID playerID;

	public NoTablesAvailable(PlayerID playerID) {
		this.playerID = playerID;
	}
	
	public PlayerID getPlayer() {
		return playerID;
	}

}
