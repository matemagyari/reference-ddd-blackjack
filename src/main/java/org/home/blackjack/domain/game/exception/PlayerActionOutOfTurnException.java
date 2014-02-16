package org.home.blackjack.domain.game.exception;

import org.home.blackjack.domain.core.PlayerId;
import org.home.blackjack.domain.exception.DomainException;

@SuppressWarnings("serial")
public class PlayerActionOutOfTurnException extends DomainException {

	public PlayerActionOutOfTurnException(PlayerId playerId) {
		super("Player tries to act out of turn " + playerId);
	}

}
