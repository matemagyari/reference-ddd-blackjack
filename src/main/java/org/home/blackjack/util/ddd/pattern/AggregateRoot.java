package org.home.blackjack.util.ddd.pattern;

import org.apache.commons.lang3.Validate;

/**
 * Class defining common behaviour for Aggregate Roots. Created to be addressed by Repositories.
 * 
 * @author michele.sollecito
 */
public abstract class AggregateRoot<T extends ID> extends Entity<T> {

	private EventBus eventBus;

	protected AggregateRoot(final T id, final EventBus eventBus) {

		super(id);
		setEventBus(eventBus);
	}

	public EventBus eventBus() {

		return eventBus;
	}

	private void setEventBus(final EventBus eventBus) {

		Validate.notNull(eventBus);
		this.eventBus = eventBus;
	}
}
