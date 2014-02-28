package org.home.blackjack.domain.table.event;

import org.home.blackjack.domain.table.core.TableID;
import org.home.blackjack.util.ddd.pattern.events.DomainEvent;

public abstract class TableEvent implements DomainEvent {

    private final TableID id;

    public TableEvent(TableID id) {
        this.id = id;
    }
    
    public TableID tableId() {
        return id;
    }
}
