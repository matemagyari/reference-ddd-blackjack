package org.home.blackjack.util.ddd.pattern.events;

import java.util.List;

@SuppressWarnings("rawtypes")
public interface SubscribableEventBus {

	<T extends DomainEvent> void register(DomainEventSubscriber<T> subscriber);
	void register(DomainEventSubscriber... subscribers);
	void register(List<DomainEventSubscriber> subscribers);
	void reset();
	void flush();

}
