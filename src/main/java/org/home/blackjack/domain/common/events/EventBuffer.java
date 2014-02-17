package org.home.blackjack.domain.common.events;

import java.util.List;

import org.home.blackjack.util.ddd.pattern.DomainEvent;
import org.home.blackjack.util.ddd.pattern.EventBus;

import com.google.common.collect.Lists;

/**
 * how to make it it threadsafe?
 * @author Mate
 *
 */
public class EventBuffer implements EventBus, SubscribableEventBus {
	
	private final List<EventSubscriber<? extends DomainEvent>> registeredSubscribers = Lists.newArrayList();
	
	private final List<DomainEvent> bufferedEvents = Lists.newArrayList();

	public void publish(DomainEvent event) {
		bufferedEvents.add(event);
		
	}

	public void register(EventSubscriber<? extends DomainEvent> subscriber) {
		registeredSubscribers.add(subscriber);
	}

	public void flush() {
		for (DomainEvent event : bufferedEvents) {
			for (EventSubscriber subscriber : registeredSubscribers) {
				if (subscriber.subscribedTo(event)) {
					subscriber.handleEvent(event);
				}
			}
		}
	}
}
