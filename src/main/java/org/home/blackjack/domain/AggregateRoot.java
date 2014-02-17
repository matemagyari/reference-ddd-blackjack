package org.home.blackjack.domain;

import org.apache.commons.lang3.Validate;
import org.home.blackjack.domain.common.EventBus;

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
