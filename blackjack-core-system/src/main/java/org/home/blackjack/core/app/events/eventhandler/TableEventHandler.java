package org.home.blackjack.core.app.events.eventhandler;

import javax.inject.Inject;
import javax.inject.Named;

import org.home.blackjack.core.app.events.external.ExternalDomainEvent;
import org.home.blackjack.core.app.events.external.ExternalEventPublisher;
import org.home.blackjack.core.domain.table.event.TableEvent;
import org.home.blackjack.core.domain.table.event.TableIsFullEvent;
import org.home.blackjack.util.ddd.pattern.app.event.DomainEventSubscriber;
import org.home.blackjack.util.ddd.pattern.domain.events.DomainEvent;

@Named
public class TableEventHandler implements DomainEventSubscriber<TableEvent> {
    
    @Inject
    private ExternalEventPublisher externalEventPublisher;

    @Override
    public boolean subscribedTo(DomainEvent event) {
        return event instanceof TableEvent && !(event instanceof TableIsFullEvent);
    }

    @Override
    public void handleEvent(TableEvent event) {
        externalEventPublisher.publish(new ExternalDomainEvent(event, event.tableId(), null));
    }

}
