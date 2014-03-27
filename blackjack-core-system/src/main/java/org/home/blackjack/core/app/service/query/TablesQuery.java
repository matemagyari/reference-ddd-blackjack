package org.home.blackjack.core.app.service.query;

import org.home.blackjack.core.app.dto.Query;
import org.home.blackjack.core.domain.shared.PlayerID;

public class TablesQuery implements Query {
    
    private final PlayerID playerID;

    public TablesQuery(PlayerID playerID) {
        this.playerID = playerID;
    }
    
    public PlayerID getPlayerID() {
        return playerID;
    }

}
