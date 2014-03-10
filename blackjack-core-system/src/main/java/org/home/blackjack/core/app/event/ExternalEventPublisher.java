package org.home.blackjack.core.app.event;


/**
 * This events go out of the Bounded Context.
 * @author Mate
 *
 */
public interface ExternalEventPublisher {
	
	void publish(ExternalDomainEvent event);

}
