package org.home.blackjack.core.domain.game.exception;

import org.home.blackjack.core.domain.shared.PlayerID;

@SuppressWarnings("serial")
public class PlayerActionAfterGameFinishedException extends PlayerActionOutOfTurnException {

	public PlayerActionAfterGameFinishedException(PlayerID playerID) {
		super(playerID);
	}

}
