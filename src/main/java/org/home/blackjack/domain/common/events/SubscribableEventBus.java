package org.home.blackjack.domain.common.events;

import org.home.blackjack.util.ddd.pattern.DomainEvent;


public interface SubscribableEventBus {
	
	void register(EventSubscriber<? extends DomainEvent> subscriber);

}
