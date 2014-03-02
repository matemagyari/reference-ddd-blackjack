package org.home.blackjack.core.domain.game.exception;

import org.home.blackjack.core.domain.game.core.GameID;
import org.home.blackjack.util.ddd.util.DomainException;

@SuppressWarnings("serial")
public class GameNotFoundException extends DomainException {

	public GameNotFoundException(GameID gameID) {
		super("Game not found for " + gameID);
	}

}
