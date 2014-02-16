package org.home.blackjack.domain.exception;

import org.home.blackjack.domain.core.PlayerId;

@SuppressWarnings("serial")
public class PlayerTriedToActAfterStandException extends DomainException {

	public PlayerTriedToActAfterStandException(PlayerId playerId) {
		super(playerId + " has tried to act after stand");
	}

}
