package org.home.blackjack.core.app.events.external;

import org.home.blackjack.util.marker.hexagonal.DrivingPort;


/**
 * This events go out of the Bounded Context.
 * @author Mate
 *
 */
public interface ExternalEventPublisher extends DrivingPort {
	
	void publish(ExternalDomainEvent event);
	void publish(ResponseDTO response);

}
