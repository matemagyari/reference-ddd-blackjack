package org.home.blackjack.domain.exception;

import org.home.blackjack.domain.PlayerId;

@SuppressWarnings("serial")
public class PlayerActionOutOfTurnException extends DomainException {

	public PlayerActionOutOfTurnException(PlayerId playerId) {
		super("Player tries to act out of turn " + playerId);
	}

}
