package org.home.blackjack.util.ddd.pattern.domain.events;


/**
 * Probably there will be an event container that will be flushed at the end of the transaction after the aggregates are
 * successfully persisted. If the events were dispatched immediately and an exception occurs later in the same
 * transaction, then the clients of the events would get inconsistent information about the state of the aggregates.
 * 
 * @author Mate
 * 
 */
public interface DomainEventPublisher {

	<T extends DomainEvent> void publish(T event);
}
