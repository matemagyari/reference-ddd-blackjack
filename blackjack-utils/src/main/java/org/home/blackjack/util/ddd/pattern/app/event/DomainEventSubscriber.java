package org.home.blackjack.util.ddd.pattern.app.event;

import org.home.blackjack.util.ddd.pattern.domain.model.DomainEvent;


public interface DomainEventSubscriber<T extends DomainEvent> {

	boolean subscribedTo(DomainEvent event);

	void handleEvent(T event);

}
