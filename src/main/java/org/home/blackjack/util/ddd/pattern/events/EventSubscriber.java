package org.home.blackjack.util.ddd.pattern.events;


public interface EventSubscriber<T extends DomainEvent> {

	boolean subscribedTo(DomainEvent event);

	void handleEvent(T event);

}
