package org.home.blackjack.domain.table.event;

import java.util.List;

import org.home.blackjack.domain.shared.PlayerID;
import org.home.blackjack.domain.table.core.TableID;

import com.google.common.collect.Lists;

public class TableSeatingChangedEvent extends TableEvent {

    private final List<PlayerID> players;

    public TableSeatingChangedEvent(TableID id, List<PlayerID> players) {
        super(id);
        this.players = Lists.newArrayList(players);
    }

    public List<PlayerID> getPlayers() {
        return Lists.newArrayList(players);
    }
}
