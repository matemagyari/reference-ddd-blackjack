package org.home.blackjack.domain.game.exception;

import org.home.blackjack.domain.shared.PlayerId;

@SuppressWarnings("serial")
public class PlayerActionAfterGameFinishedException extends PlayerActionOutOfTurnException {

	public PlayerActionAfterGameFinishedException(PlayerId playerId) {
		super(playerId);
	}

}
