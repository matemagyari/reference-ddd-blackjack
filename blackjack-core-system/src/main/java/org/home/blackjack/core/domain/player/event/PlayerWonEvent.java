package org.home.blackjack.core.domain.player.event;

import org.home.blackjack.core.domain.shared.PlayerID;
import org.home.blackjack.util.ddd.pattern.ValueObject;
import org.home.blackjack.util.ddd.pattern.events.DomainEvent;

public class PlayerWonEvent  extends ValueObject implements DomainEvent {

	private PlayerID playerID;

	public PlayerWonEvent(PlayerID id) {
		this.playerID = id;
	}
	
	public PlayerID getPlayerID() {
		return playerID;
	}

}
