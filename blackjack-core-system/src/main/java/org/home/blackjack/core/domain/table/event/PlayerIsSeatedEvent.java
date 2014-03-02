package org.home.blackjack.core.domain.table.event;

import org.home.blackjack.core.domain.shared.PlayerID;
import org.home.blackjack.core.domain.shared.TableID;

public class PlayerIsSeatedEvent extends TableEvent {

    private final PlayerID player;

    public PlayerIsSeatedEvent(TableID id, PlayerID player) {
        super(id);
        this.player = player;
    }
    
    public PlayerID getPlayer() {
        return player;
    }

}
