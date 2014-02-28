package org.home.blackjack.app.eventhandler;

import javax.inject.Inject;
import javax.inject.Named;

import org.home.blackjack.domain.game.Dealer;
import org.home.blackjack.domain.table.event.TableIsFullEvent;
import org.home.blackjack.util.ddd.pattern.events.DomainEvent;
import org.home.blackjack.util.ddd.pattern.events.EventSubscriber;

@Named
public class TableIsFullEventHandler implements EventSubscriber<TableIsFullEvent> {
    
    @Inject
    private Dealer dealer;

    public boolean subscribedTo(DomainEvent event) {
        return event instanceof TableIsFullEvent;
    }

    public void handleEvent(TableIsFullEvent event) {
    	dealer.startANewGameOnTable(event.tableId(), event.players());
    }

}
