package org.home.blackjack.domain.common;


/**
 * Probably there will be an event container that will be flushed at the end of
 * the transaction after the aggregates are successfully persisted. If the
 * events were dispatched immediately and an exception occurs later in the same
 * transaction, then the clients of the events would get inconsistent
 * information about the state of the aggregates.
 * 
 * @author Mate
 * 
 */
public interface EventDispatcher {

	void dispatch(DomainEvent event);

}
