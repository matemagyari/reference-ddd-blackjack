package org.home.blackjack.app.eventhandler;

import javax.inject.Inject;
import javax.inject.Named;

import org.home.blackjack.app.event.ExternalEventPublisher;
import org.home.blackjack.domain.table.event.TableEvent;
import org.home.blackjack.util.ddd.pattern.events.DomainEvent;
import org.home.blackjack.util.ddd.pattern.events.EventSubscriber;

@Named
public class TableEventHandler implements EventSubscriber<TableEvent> {
    
    @Inject
    private ExternalEventPublisher externalEventPublisher;

    @Override
    public boolean subscribedTo(DomainEvent event) {
        return event instanceof TableEvent;
    }

    @Override
    public void handleEvent(TableEvent event) {
        externalEventPublisher.publish(event);
    }

}
