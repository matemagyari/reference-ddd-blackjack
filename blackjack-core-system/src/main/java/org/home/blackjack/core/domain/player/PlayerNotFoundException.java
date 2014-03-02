package org.home.blackjack.core.domain.player;

import org.home.blackjack.core.domain.shared.PlayerID;
import org.home.blackjack.util.ddd.util.DomainException;

@SuppressWarnings("serial")
public class PlayerNotFoundException extends DomainException {

    public PlayerNotFoundException(PlayerID playerID) {
        super(playerID + " not found");
    }

}
