package org.home.blackjack.util.ddd.pattern.domain.model;

import org.home.blackjack.util.ddd.pattern.domain.events.DomainEventPublisher;

/**
 * Class defining common behaviour for Aggregate Roots. Created to be addressed by Repositories.
 * 
 * @author michele.sollecito
 */
public abstract class AggregateRoot<T extends ID> extends Entity<T> {

	private transient DomainEventPublisher domainEventPublisher;

	protected AggregateRoot(final T id, final DomainEventPublisher domainEventPublisher) {
		super(id);
        this.domainEventPublisher = domainEventPublisher;
	}
	protected AggregateRoot(final T id) {
	   this(id, null);
	}

	protected DomainEventPublisher domainEventPublisher() {
		return domainEventPublisher;
	}
	
	public void setDomainEventPublisher(DomainEventPublisher domainEventPublisher) {
		this.domainEventPublisher = domainEventPublisher;
	}

}
