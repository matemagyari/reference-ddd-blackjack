package org.home.blackjack.core.infrastructure.events;

import org.home.blackjack.util.ddd.pattern.events.DomainEvent;
import org.home.blackjack.util.ddd.pattern.events.DomainEventPublisher;

/**
 * Base EventBus implementation based on Guava.
 * 
 * @author michele.sollecito
 */
// @Named
public class GuavaEventBus implements DomainEventPublisher {

    @Override
	public void publish(final DomainEvent event) {

		// TODO implement based on Guava library
	}

	// TODO add methods for registration
}
