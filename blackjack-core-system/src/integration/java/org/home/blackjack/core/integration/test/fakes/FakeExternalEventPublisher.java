package org.home.blackjack.core.integration.test.fakes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.home.blackjack.core.app.events.external.ExternalDomainEvent;
import org.home.blackjack.core.app.events.external.ExternalEventPublisher;
import org.home.blackjack.core.app.events.external.ResponseDTO;
import org.home.blackjack.core.app.service.query.TablesDTO;
import org.home.blackjack.core.domain.game.core.GameID;
import org.home.blackjack.core.domain.game.event.GameStartedEvent;
import org.home.blackjack.core.domain.shared.TableID;
import org.home.blackjack.core.integration.test.util.Util;
import org.home.blackjack.util.ddd.pattern.events.DomainEvent;
import org.junit.Assert;

import com.google.common.collect.Lists;

public class FakeExternalEventPublisher implements ExternalEventPublisher {

	private final List<DomainEvent> events = Lists.newArrayList();
	private final List<ResponseDTO> responses = Lists.newArrayList();

	@Override
	public void publish(ExternalDomainEvent event) {
		System.err.println("External event: " + event);
		events.add(event.getEvent());
	}
	

	@Override
	public void publish(ResponseDTO response) {
		System.err.println("External response: " + response);
		responses.add(response);
	}

	public GameID assertInitalCardsDealtEvent(TableID tableID) {
		Util.sleep(500);
		
		for (DomainEvent event : events) {
			if (event instanceof GameStartedEvent) {
				GameStartedEvent dealtEvent = (GameStartedEvent) event;
				assertEquals(tableID, dealtEvent.getTableID());
				return  dealtEvent.getGameID();
			}
		}
		Assert.fail();
		return null;
	}
	
	public void reset() {
		events.clear();
		responses.clear();
	}

	public void assertDispatched(DomainEvent event) {
		assertTrue(events.contains(event));
	}
	
	public <T extends DomainEvent> void assertArrived(Class<T> clazz, DomainEventMatcher<T> matcher) {
		assertNotNull(consume(clazz, matcher));
	}
	public <T extends DomainEvent> T consume(Class<T> clazz, DomainEventMatcher<T> matcher) {
		Util.sleep(100);
		for (DomainEvent event : events) {
			if (event.getClass().equals(clazz) && matcher.match((T) event)) {
				events.remove(event);
				return (T) event;
			}
		}
		throw new IllegalStateException("Domain  event not found");
	}
	
	public static interface DomainEventMatcher <T extends DomainEvent> {
		boolean match(T event);
	}

	public TablesDTO assertArrived(TablesDTO tablesDTO) {
		Util.sleep(100);
		for (ResponseDTO response : responses) {
			if (response.equals(tablesDTO)) {
				responses.remove(response);
				return (TablesDTO) response;
			}
		}
		throw new IllegalStateException("Response  event not found");
	}


}
