package org.home.blackjack.domain;

/**
 * Class defining common behaviour for Aggregate Roots. Created to be addressed by Repositories.
 * 
 * @author michele.sollecito
 */
public abstract class AggregateRoot<T extends ID> extends Entity<T> {

	protected AggregateRoot(final T id) {

		super(id);
	}
}
