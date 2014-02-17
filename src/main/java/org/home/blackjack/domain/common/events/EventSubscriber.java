package org.home.blackjack.domain.common.events;

import org.home.blackjack.util.ddd.pattern.DomainEvent;

public interface EventSubscriber<T extends DomainEvent> {

	boolean subscribedTo(T event);

	void handleEvent(T event);

}
