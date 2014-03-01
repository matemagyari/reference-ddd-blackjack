package org.home.blackjack.util.ddd.pattern.events;



public interface SubscribableEventBus {
	
	<T extends DomainEvent> void register(DomainEventSubscriber<T> subscriber);
	@SuppressWarnings("rawtypes")
	void register(DomainEventSubscriber... subscribers);
	void reset();
	void flush();

}
