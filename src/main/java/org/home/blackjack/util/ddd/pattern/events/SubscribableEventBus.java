package org.home.blackjack.util.ddd.pattern.events;



public interface SubscribableEventBus {
	
	<T extends DomainEvent> void register(DomainEventSubscriber<T> subscriber);
	void reset();
	void flush();

}
