package org.home.blackjack.domain.game.exception;

import org.home.blackjack.domain.common.DomainException;
import org.home.blackjack.domain.shared.PlayerId;

@SuppressWarnings("serial")
public class PlayerTriedToActAfterStandException extends DomainException {

	public PlayerTriedToActAfterStandException(PlayerId playerId) {
		super(playerId + " has tried to act after stand");
	}

}
