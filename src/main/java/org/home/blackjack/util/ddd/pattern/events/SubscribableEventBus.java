package org.home.blackjack.util.ddd.pattern.events;



public interface SubscribableEventBus {
	
	void register(EventSubscriber<? extends DomainEvent> subscriber);

	void flush();

}
