package org.home.blackjack.util.ddd.pattern;

import org.apache.commons.lang3.Validate;

/**
 * Class defining common behaviour for Aggregate Roots. Created to be addressed by Repositories.
 * 
 * @author michele.sollecito
 */
public abstract class AggregateRoot<T extends ID> extends Entity<T> {

	private final EventPublisher eventPublisher;

	protected AggregateRoot(final T id, final EventPublisher eventPublisher) {
		super(id);
		Validate.notNull(eventPublisher);
        this.eventPublisher = eventPublisher;
	}

	public EventPublisher eventPublisher() {
		return eventPublisher;
	}

}
