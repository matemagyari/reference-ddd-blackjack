package org.home.blackjack.domain.game.exception;

import org.home.blackjack.domain.core.PlayerId;
import org.home.blackjack.domain.exception.DomainException;

@SuppressWarnings("serial")
public class PlayerTriedToActAfterStandException extends DomainException {

	public PlayerTriedToActAfterStandException(PlayerId playerId) {
		super(playerId + " has tried to act after stand");
	}

}
