package org.home.blackjack;

import java.util.List;

import javax.inject.Named;

import org.home.blackjack.util.ddd.pattern.events.DomainEvent;
import org.home.blackjack.util.ddd.pattern.events.EventPublisher;

import com.google.common.collect.Lists;

@Named
public class EventPublisherStub implements EventPublisher {

	private final List<DomainEvent> eventsInChronologicalOrder = Lists.newArrayList();

	public void publish(DomainEvent event) {
		eventsInChronologicalOrder.add(event);
	}

	public void print() {
		for (DomainEvent event : eventsInChronologicalOrder) {
			System.out.println(event);
		}
	}

	public DomainEvent last() {
		return eventsInChronologicalOrder.get(eventsInChronologicalOrder.size() - 1);
	}

	DomainEvent get(int order) {
		return eventsInChronologicalOrder.get(order - 1);
	}

}
