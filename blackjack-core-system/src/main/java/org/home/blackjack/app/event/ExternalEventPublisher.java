package org.home.blackjack.app.event;

import org.home.blackjack.util.ddd.pattern.events.DomainEvent;

/**
 * This events go out of the Bounded Context.
 * @author Mate
 *
 */
public interface ExternalEventPublisher {
	
	void publish(DomainEvent event);

}
