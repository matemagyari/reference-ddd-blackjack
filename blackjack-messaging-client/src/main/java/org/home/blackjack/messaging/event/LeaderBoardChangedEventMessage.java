package org.home.blackjack.messaging.event;

import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.home.blackjack.messaging.common.Message;

import com.google.common.collect.Lists;

public class LeaderBoardChangedEventMessage extends Message {

    public final List<LeaderBoardRecordMessage> records = Lists.newArrayList();

    public LeaderBoardChangedEventMessage(List<LeaderBoardRecordMessage> records) {
        this.records.addAll(records);
    }

    public static class LeaderBoardRecordMessage  {
        public final String playerName;
        public final int winNumber;

        public LeaderBoardRecordMessage(String playerName,int winNumber ) {
            this.playerName = playerName;
            this.winNumber = winNumber;
        }
        
    	@Override
    	public boolean equals(final Object other) {
    		return EqualsBuilder.reflectionEquals(this, other);
    	}

    	@Override
    	public int hashCode() {
    		return HashCodeBuilder.reflectionHashCode(this);
    	}
        
    }
}
