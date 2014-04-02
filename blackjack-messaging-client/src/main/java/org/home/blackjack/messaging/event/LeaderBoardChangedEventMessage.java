package org.home.blackjack.messaging.event;

import java.util.List;

import com.google.common.collect.Lists;

public class LeaderBoardChangedEventMessage {

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
        
    }

}
