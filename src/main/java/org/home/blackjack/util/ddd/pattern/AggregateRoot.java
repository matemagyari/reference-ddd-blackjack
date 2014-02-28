package org.home.blackjack.util.ddd.pattern;

import org.apache.commons.lang3.Validate;
import org.home.blackjack.util.ddd.pattern.events.EventPublisher;

/**
 * Class defining common behaviour for Aggregate Roots. Created to be addressed by Repositories.
 * 
 * @author michele.sollecito
 */
public abstract class AggregateRoot<T extends ID> extends Entity<T> {

	private transient EventPublisher eventPublisher;

	protected AggregateRoot(final T id, final EventPublisher eventPublisher) {
		super(id);
		Validate.notNull(eventPublisher);
        this.eventPublisher = eventPublisher;
	}
	protected AggregateRoot(final T id) {
	   this(id, null);
	}

	protected EventPublisher eventPublisher() {
		return eventPublisher;
	}
	
	public void setEventPublisher(EventPublisher eventPublisher) {
		this.eventPublisher = eventPublisher;
	}

}
