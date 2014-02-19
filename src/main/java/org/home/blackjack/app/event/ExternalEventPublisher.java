package org.home.blackjack.app.event;

import org.home.blackjack.util.ddd.pattern.DomainEvent;

public interface ExternalEventPublisher {
	
	void publish(DomainEvent event);

}
