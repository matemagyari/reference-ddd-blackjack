package org.home.blackjack.util.ddd.pattern.events;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Named;


/**
 * @author Mate
 *
 */
@Named
public class EventBuffer implements EventPublisher, SubscribableEventBus {
	
	private final ThreadLocal<List<EventSubscriber<? extends DomainEvent>>> registeredSubscribers = new ThreadLocal<List<EventSubscriber<? extends DomainEvent>>>();
	private final ThreadLocal<List<DomainEvent>> bufferedEvents = new ThreadLocal<List<DomainEvent>>();
	
	private final Executor executor;
	
	public EventBuffer() {
		registeredSubscribers.set(new ArrayList<EventSubscriber<? extends DomainEvent>>());
		bufferedEvents.set(new ArrayList<DomainEvent>());
		executor = Executors.newFixedThreadPool(100);
	}

	public void publish(DomainEvent event) {
		bufferedEvents.get().add(event);
	}

	public void register(EventSubscriber<? extends DomainEvent> subscriber) {
		registeredSubscribers.get().add(subscriber);
	}

	public void flush() {
		List<DomainEvent> events = bufferedEvents.get();
		while (!events.isEmpty()) {
			process(events.remove(0));
		}
	}

	private void process(final DomainEvent nextEvent) {
		for (final EventSubscriber subscriber : registeredSubscribers.get()) {
			if (subscriber.subscribedTo(nextEvent)) {
				executor.execute(new Runnable() {
					@Override
					public void run() {
						subscriber.handleEvent(nextEvent);
					}
				});
			}
		}
	}
}
