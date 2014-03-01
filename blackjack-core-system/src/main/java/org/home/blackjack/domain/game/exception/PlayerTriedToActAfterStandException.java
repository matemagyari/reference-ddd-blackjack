package org.home.blackjack.domain.game.exception;

import org.home.blackjack.domain.common.DomainException;
import org.home.blackjack.domain.shared.PlayerID;

@SuppressWarnings("serial")
public class PlayerTriedToActAfterStandException extends DomainException {

	public PlayerTriedToActAfterStandException(PlayerID playerID) {
		super(playerID + " has tried to act after stand");
	}

}
