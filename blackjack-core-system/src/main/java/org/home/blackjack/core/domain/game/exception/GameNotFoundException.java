package org.home.blackjack.core.domain.game.exception;

import org.home.blackjack.core.domain.game.core.GameID;
import org.home.blackjack.core.domain.shared.TableID;
import org.home.blackjack.util.ddd.pattern.domain.exception.DomainException;

@SuppressWarnings("serial")
public class GameNotFoundException extends DomainException {

	public GameNotFoundException(GameID gameID) {
		super("Game not found for " + gameID);
	}

	public GameNotFoundException(TableID tableId) {
		super("Game not found for table " + tableId);
	}

}
