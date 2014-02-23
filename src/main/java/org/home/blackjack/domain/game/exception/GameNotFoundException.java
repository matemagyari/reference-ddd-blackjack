package org.home.blackjack.domain.game.exception;

import org.home.blackjack.domain.common.DomainException;
import org.home.blackjack.domain.game.core.GameID;

@SuppressWarnings("serial")
public class GameNotFoundException extends DomainException {

	public GameNotFoundException(GameID gameID) {
		super("Game not found for " + gameID);
	}

}
