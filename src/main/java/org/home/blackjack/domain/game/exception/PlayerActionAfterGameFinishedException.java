package org.home.blackjack.domain.game.exception;

import org.home.blackjack.domain.player.PlayerID;

@SuppressWarnings("serial")
public class PlayerActionAfterGameFinishedException extends PlayerActionOutOfTurnException {

	public PlayerActionAfterGameFinishedException(PlayerID playerID) {
		super(playerID);
	}

}
