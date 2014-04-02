package org.home.blackjack.core.domain.player.event;

import java.util.List;

import org.home.blackjack.core.domain.player.core.PlayerName;
import org.home.blackjack.util.ddd.pattern.ValueObject;
import org.home.blackjack.util.ddd.pattern.events.DomainEvent;

import com.google.common.collect.Lists;

public class LeaderBoardChangedEvent extends ValueObject implements DomainEvent {

    private final List<LeaderBoardRecord> records = Lists.newArrayList();

    public LeaderBoardChangedEvent(List<LeaderBoardRecord> records) {
        this.records.addAll(records);
    }

    public static class LeaderBoardRecord extends ValueObject {
        private final PlayerName playerName;
        private final int winNumber;

        public LeaderBoardRecord(PlayerName playerName,int winNumber ) {
            this.playerName = playerName;
            this.winNumber = winNumber;
        }

		@Override
		public String toString() {
			return "[playerName=" + playerName + ", winNumber=" + winNumber + "]";
		}
        
    }

	@Override
	public String toString() {
		return "[records=" + records + "]";
	}
    
    

}
