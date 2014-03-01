package org.home.blackjack.domain.player;

import org.home.blackjack.domain.common.DomainException;
import org.home.blackjack.domain.shared.PlayerID;

@SuppressWarnings("serial")
public class PlayerNotFoundException extends DomainException {

    public PlayerNotFoundException(PlayerID playerID) {
        super(playerID + " not found");
    }

}
