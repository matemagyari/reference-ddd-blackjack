package org.home.blackjack.core.app.service.query;

import org.home.blackjack.core.app.dto.Query;
import org.home.blackjack.core.domain.shared.PlayerID;

public class TablesQuery implements Query {
    
    private final String playerId;

    public TablesQuery(String playerID) {
        this.playerId = playerID;
    }
    
    public PlayerID getPlayerID() {
        return PlayerID.createFrom(playerId);
    }

}
