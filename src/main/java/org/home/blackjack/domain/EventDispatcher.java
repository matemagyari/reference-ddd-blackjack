package org.home.blackjack.domain;

import org.home.blackjack.domain.event.DomainEvent;

public interface EventDispatcher {

	void dispatch(DomainEvent event);

}
