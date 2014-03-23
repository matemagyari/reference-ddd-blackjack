package org.home.blackjack.core.domain.table.event;

import org.home.blackjack.core.domain.shared.TableID;
import org.home.blackjack.util.ddd.pattern.ValueObject;
import org.home.blackjack.util.ddd.pattern.events.DomainEvent;

public abstract class TableEvent extends ValueObject implements DomainEvent {

    private final TableID id;

    public TableEvent(TableID id) {
        this.id = id;
    }
    
    public TableID tableId() {
        return id;
    }
}
