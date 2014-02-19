package org.home.blackjack.domain.player.event;

import org.home.blackjack.domain.shared.PlayerID;
import org.home.blackjack.util.ddd.pattern.DomainEvent;

public class PlayerWonEvent implements DomainEvent {

	private PlayerID playerID;

	public PlayerWonEvent(PlayerID id) {
		this.playerID = id;
	}
	
	public PlayerID getPlayerID() {
		return playerID;
	}

}
