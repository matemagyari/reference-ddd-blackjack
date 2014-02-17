package org.home.blackjack.domain.common.events;

import org.home.blackjack.util.ddd.pattern.DomainEvent;

public interface EventMatcher {
	
	boolean match(DomainEvent event);

}
