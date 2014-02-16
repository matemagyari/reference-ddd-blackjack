package org.home.blackjack.domain.exception;

import org.home.blackjack.domain.PlayerId;

@SuppressWarnings("serial")
public class PlayerActionAfterGameFinishedException extends PlayerActionOutOfTurnException {

	public PlayerActionAfterGameFinishedException(PlayerId playerId) {
		super(playerId);
	}

}
