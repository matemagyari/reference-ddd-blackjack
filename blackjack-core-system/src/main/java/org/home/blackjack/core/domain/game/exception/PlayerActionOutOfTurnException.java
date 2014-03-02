package org.home.blackjack.core.domain.game.exception;

import org.home.blackjack.core.domain.shared.PlayerID;
import org.home.blackjack.util.ddd.util.DomainException;

@SuppressWarnings("serial")
public class PlayerActionOutOfTurnException extends DomainException {

	public PlayerActionOutOfTurnException(PlayerID playerID) {
		super("Player tries to act out of turn " + playerID);
	}

}
