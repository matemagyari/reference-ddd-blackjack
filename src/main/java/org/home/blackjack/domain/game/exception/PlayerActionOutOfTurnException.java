package org.home.blackjack.domain.game.exception;

import org.home.blackjack.domain.common.DomainException;
import org.home.blackjack.domain.shared.PlayerId;

@SuppressWarnings("serial")
public class PlayerActionOutOfTurnException extends DomainException {

	public PlayerActionOutOfTurnException(PlayerId playerId) {
		super("Player tries to act out of turn " + playerId);
	}

}
