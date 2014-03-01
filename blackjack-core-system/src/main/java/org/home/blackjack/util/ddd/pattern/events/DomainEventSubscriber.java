package org.home.blackjack.util.ddd.pattern.events;


public interface DomainEventSubscriber<T extends DomainEvent> {

	boolean subscribedTo(DomainEvent event);

	void handleEvent(T event);

}
