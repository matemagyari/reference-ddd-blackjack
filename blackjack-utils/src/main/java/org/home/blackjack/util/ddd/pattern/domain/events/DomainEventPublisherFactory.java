package org.home.blackjack.util.ddd.pattern.domain.events;

import org.home.blackjack.util.ddd.pattern.domain.model.DomainEventPublisher;

public interface DomainEventPublisherFactory {

	DomainEventPublisher domainEventPublisherInstance();
}
