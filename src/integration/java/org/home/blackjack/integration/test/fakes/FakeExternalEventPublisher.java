package org.home.blackjack.integration.test.fakes;

import static org.junit.Assert.*;

import java.util.List;

import org.home.blackjack.app.event.ExternalEventPublisher;
import org.home.blackjack.domain.game.core.GameID;
import org.home.blackjack.domain.game.event.GameFinishedEvent;
import org.home.blackjack.domain.game.event.GameStartedEvent;
import org.home.blackjack.domain.table.core.TableID;
import org.home.blackjack.util.ddd.pattern.events.DomainEvent;
import org.junit.Assert;

import com.google.common.collect.Lists;

public class FakeExternalEventPublisher implements ExternalEventPublisher {

	private final List<DomainEvent> events = Lists.newArrayList();

	@Override
	public void publish(DomainEvent event) {
		events.add(event);
	}

	public GameID assertInitalCardsDealtEvent(TableID tableID) {
		for (DomainEvent event : events) {
			if (event instanceof GameStartedEvent) {
				GameStartedEvent dealtEvent = (GameStartedEvent) event;
				assertEquals(tableID, dealtEvent.tableId());
				return  dealtEvent.getGameID();
			}
		}
		Assert.fail();
		return null;
	}
	
	public void reset() {
		events.clear();
	}

	public void assertDispatched(DomainEvent event) {
		assertTrue(events.contains(event));
	}

}
