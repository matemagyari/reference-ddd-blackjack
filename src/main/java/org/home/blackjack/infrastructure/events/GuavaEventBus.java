package org.home.blackjack.infrastructure.events;

import org.home.blackjack.util.ddd.pattern.DomainEvent;
import org.home.blackjack.util.ddd.pattern.EventBus;

/**
 * Base EventBus implementation based on Guava.
 * 
 * @author michele.sollecito
 */
// @Named
public class GuavaEventBus implements EventBus {

    @Override
	public void publish(final DomainEvent event) {

		// TODO implement based on Guava library
	}

	// TODO add methods for registration
}
