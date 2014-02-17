package org.home.blackjack.domain.game.exception;

import org.home.blackjack.domain.common.DomainException;
import org.home.blackjack.domain.player.PlayerID;

@SuppressWarnings("serial")
public class PlayerActionOutOfTurnException extends DomainException {

	public PlayerActionOutOfTurnException(PlayerID playerID) {
		super("Player tries to act out of turn " + playerID);
	}

}
