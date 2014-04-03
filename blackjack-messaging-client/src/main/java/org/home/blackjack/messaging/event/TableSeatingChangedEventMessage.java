package org.home.blackjack.messaging.event;

import java.util.List;

import org.home.blackjack.messaging.common.Message;

import com.google.common.collect.Lists;

public class TableSeatingChangedEventMessage extends Message {

    public final List<String> players;
    public final String id;

    public TableSeatingChangedEventMessage(String id, List<String> players) {
        this.id = id;
        this.players = Lists.newArrayList(players);
    }

}
