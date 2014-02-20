package org.home.blackjack.domain.table.event;

import java.util.List;

import org.home.blackjack.domain.shared.PlayerID;
import org.home.blackjack.domain.table.TableID;

import com.google.common.collect.Lists;

public class TableIsFullEvent extends TableEvent {

    private final List<PlayerID> players = Lists.newArrayList();

    public TableIsFullEvent(TableID id, List<PlayerID> players) {
        super(id);
        this.players.addAll(players);
    }

}
