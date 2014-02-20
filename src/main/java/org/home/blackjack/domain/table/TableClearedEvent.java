package org.home.blackjack.domain.table;

import org.home.blackjack.domain.table.event.TableEvent;

public class TableClearedEvent extends TableEvent {

    public TableClearedEvent(TableID id) {
        super(id);
    }

}
