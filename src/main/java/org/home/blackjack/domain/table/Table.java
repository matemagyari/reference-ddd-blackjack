package org.home.blackjack.domain.table;

import java.util.List;

import org.home.blackjack.domain.common.DomainException;
import org.home.blackjack.domain.shared.PlayerID;
import org.home.blackjack.domain.table.event.PlayerIsSeatedEvent;
import org.home.blackjack.domain.table.event.TableEvent;
import org.home.blackjack.domain.table.event.TableIsFullEvent;
import org.home.blackjack.util.ddd.pattern.AggregateRoot;
import org.home.blackjack.util.ddd.pattern.EventPublisher;

import com.google.common.collect.Lists;

public class Table extends AggregateRoot<TableID> {
    
    //currently we only use tables of size 2
    private final int size = 2;
    private final List<PlayerID> players = Lists.newArrayList();
    
    public Table(TableID id, EventPublisher eventPublisher) {
        super(id, eventPublisher);
    }
    
    public void playerSits(PlayerID player) {
        if (players.contains(player)) {
            throw new DomainException(player + " already sits by this table");
        }
        players.add(player);
        publish(new PlayerIsSeatedEvent(getID(), player));
        if (players.size() == size) {
            publish(new TableIsFullEvent(getID(), players));
        }
    }
    
    public void clearTable() {
        players.clear();
        publish(new TableClearedEvent(getID()));
    }
    
    private void publish(TableEvent event) {
        eventPublisher().publish(event);
    }
    
}
