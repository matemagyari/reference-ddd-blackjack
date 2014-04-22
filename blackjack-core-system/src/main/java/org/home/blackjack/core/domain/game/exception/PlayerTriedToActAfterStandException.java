package org.home.blackjack.core.domain.game.exception;

import org.home.blackjack.core.domain.shared.PlayerID;
import org.home.blackjack.util.ddd.pattern.domain.exception.DomainException;

@SuppressWarnings("serial")
public class PlayerTriedToActAfterStandException extends DomainException {

	public PlayerTriedToActAfterStandException(PlayerID playerID) {
		super(playerID + " has tried to act after stand");
	}

}
