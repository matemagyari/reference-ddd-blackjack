package org.home.blackjack.core.domain.player.event;

import java.util.List;

import org.home.blackjack.core.domain.player.Player;
import org.home.blackjack.core.domain.player.PlayerName;
import org.home.blackjack.util.ddd.pattern.events.DomainEvent;

import com.google.common.collect.Lists;

public class LeaderBoardChangedEvent implements DomainEvent {

    private final List<LeaderBoardRecord> records = Lists.newArrayList();

    public LeaderBoardChangedEvent(List<LeaderBoardRecord> records) {
        this.records.addAll(records);
    }

    public static class LeaderBoardRecord {
        private final PlayerName playerName;
        private final int winNumber;

        public LeaderBoardRecord(Player player) {
            this.playerName = player.getName();
            this.winNumber = player.getWinNumber();
        }
    }

}
