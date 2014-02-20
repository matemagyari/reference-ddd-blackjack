package org.home.blackjack.domain.game.event;

import org.home.blackjack.domain.shared.PlayerID;
import org.home.blackjack.util.ddd.pattern.DomainEvent;

public class NoTablesAvailable implements DomainEvent {

	private final PlayerID playerID;

	public NoTablesAvailable(PlayerID playerID) {
		this.playerID = playerID;
	}
	
	public PlayerID getPlayer() {
		return playerID;
	}

}
