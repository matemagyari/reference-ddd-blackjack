package org.home.blackjack.util.ddd.pattern;

import org.apache.commons.lang3.Validate;

/**
 * Class defining common behaviour for Aggregate Roots. Created to be addressed by Repositories.
 * 
 * @author michele.sollecito
 */
public abstract class AggregateRoot<T extends ID> extends Entity<T> {

	private EventPublisher eventBus;

	protected AggregateRoot(final T id, final EventPublisher eventBus) {

		super(id);
		setEventBus(eventBus);
	}

	public EventPublisher eventBus() {

		return eventBus;
	}

	private void setEventBus(final EventPublisher eventBus) {

		Validate.notNull(eventBus);
		this.eventBus = eventBus;
	}
}
