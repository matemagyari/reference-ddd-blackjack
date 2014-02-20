package org.home.blackjack.domain.table.event;

import org.home.blackjack.domain.shared.PlayerID;
import org.home.blackjack.domain.table.TableID;

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
