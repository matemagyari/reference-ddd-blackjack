package org.home.blackjack.core.domain.game.event;

import org.apache.commons.lang3.Validate;
import org.home.blackjack.core.domain.game.core.GameID;
import org.home.blackjack.core.domain.shared.PlayerID;

public class PlayerStandsEvent extends GameEvent {

	private final PlayerID player;

	public PlayerStandsEvent(GameID gameID, int sequenceNumber, PlayerID player) {
		super(gameID, sequenceNumber);
		Validate.notNull(player);
		this.player = player;
	}

	public PlayerID getPlayer() {
		return player;
	}

	@Override
	public String toString() {
		return "PlayerStandsEvent [player=" + player + ", gameID=" + gameID + ", sequenceNumber=" + sequenceNumber
				+ "]";
	}

}
