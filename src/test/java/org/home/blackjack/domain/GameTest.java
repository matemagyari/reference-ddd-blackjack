package org.home.blackjack.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.home.blackjack.TestFixture;
import org.home.blackjack.domain.Card.Rank;
import org.home.blackjack.domain.Card.Suite;
import org.home.blackjack.domain.event.DomainEvent;
import org.home.blackjack.domain.event.GameFinishedEvent;
import org.home.blackjack.domain.event.PlayerCardDealtEvent;
import org.home.blackjack.domain.exception.PlayerActionOutOfTurnException;
import org.home.blackjack.domain.exception.PlayerTriedToActAfterStandException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.common.collect.Lists;

/**
 * This is not a unit test, but testing the Game mechanism as a whole.
 * It tests the Game mechanism, and check the events.
 * 
 * @author Mate
 * 
 */
@RunWith(MockitoJUnitRunner.class)
public class GameTest {

	private Game testObj;
	private PlayerId dealer = TestFixture.aPlayer();
	private PlayerId player = TestFixture.aPlayer();
	private GameId gameId = new GameId(12l);
	private AtomicInteger actionCounter = new AtomicInteger();
	
	@Mock
	private Deck deck;
	@Mock
	private DeckFactory deckFactory;
	private EventDispatcherStub eventDispatcher;
	
	
	@Before
	public void setup() {
		when(deckFactory.createNew()).thenReturn(deck);
		eventDispatcher = new EventDispatcherStub();
		testObj = new Game(dealer, player, deckFactory, eventDispatcher);
		testObj.setGameId(gameId);
	}

	@Test(expected = PlayerActionOutOfTurnException.class)
	public void dealerTriesToMakeTheFirstHit() {
		prepareDeckForLongerGame();
		dealInitialCards();
		dealerDraws();
	}

	@Test(expected = PlayerActionOutOfTurnException.class)
	public void playerTriesToDrawTwiceInARow() {
		prepareDeckForLongerGame();
		dealInitialCards();
		playerDraws();
		playerDraws();
	}

	
	@Test(expected = PlayerActionOutOfTurnException.class)
	public void playerTriesToDrawThenStopInARow() {
		prepareDeckForLongerGame();
		dealInitialCards();
		playerDraws();
		playerStops();
	}

	
	@Test(expected = PlayerTriedToActAfterStandException.class)
	public void playerTriesToActAfterStand() {
		prepareDeckForLongerGame();
		dealInitialCards();
		playerStops();
		dealerDraws();
		playerDraws();
	}
	
	@Test
	public void playerWinsWithBlackJack() {
		prepareDeckInOrder(
				card(Suite.CLUB, Rank.ACE)
				, card(Suite.SPADE, Rank.KING)
				, card(Suite.DIAMOND, Rank.TEN)
				, card(Suite.HEART, Rank.TEN)
				);
		dealInitialCards();
		playerStops();
		dealerStops();
		assertTheWinnerIs(player);
	}	
	
	@Test
	public void dealerWinsWithBlackJack() {
		prepareDeckInOrder(
				card(Suite.CLUB, Rank.KING)
				, card(Suite.SPADE, Rank.ACE)
				, card(Suite.DIAMOND, Rank.TEN)
				, card(Suite.HEART, Rank.JACK)
				);
		dealInitialCards();
		playerStops();
		dealerStops();
		assertTheWinnerIs(dealer);
	}

	@Test
	public void playerWinsAfterSeveralHitsWith18Against17() {
		prepareDeckInOrder(
				card(Suite.CLUB, Rank.TWO)
				, card(Suite.SPADE, Rank.TWO)
				, card(Suite.DIAMOND, Rank.SEVEN)
				, card(Suite.HEART, Rank.JACK)
				, card(Suite.DIAMOND, Rank.FIVE)
				, card(Suite.HEART, Rank.THREE)
				, card(Suite.DIAMOND, Rank.FOUR)
				, card(Suite.HEART, Rank.TWO)
				);
		dealInitialCards();
		playerDraws();
		dealerDraws();
		playerDraws();
		dealerDraws();
		playerStops();
		dealerStops();
		assertTheWinnerIs(player);
	}
	
	private void assertTheWinnerIs(PlayerId winner) {
		assertTrue(testObj.isFinished());
		assertEquals(new GameFinishedEvent(gameId, actionCounter.get(), winner), eventDispatcher.last());
	}
	
	private void dealInitialCards() {
		testObj.dealInitialCards();
		actionCounter.incrementAndGet();
		actionCounter.incrementAndGet();
		actionCounter.incrementAndGet();
		actionCounter.incrementAndGet();
	}

	private void playerDraws() {
		hits(player, dealer);
	}

	private void dealerDraws() {
		hits(dealer, player);
	}
	
	private void hits(PlayerId playerToAct, PlayerId otherPlayer) {
		try {
			testObj.playerHits(playerToAct);
			actionCounter.incrementAndGet();
			/*
			PlayerCardDealtEvent domainEvent = (PlayerCardDealtEvent) eventDispatcher.get(actionCounter.get()-1);
			assertEquals(gameId, domainEvent.getGameId());
			assertEquals(actionCounter.get(), domainEvent.getSequenceNumber());
			assertEquals(playerToAct, domainEvent.getPlayer());
			assertEquals(otherPlayer, domainEvent.getOtherPlayer());
			*/
		} catch (RuntimeException ex) {
			throw ex;
		}
	}
	
	private void playerStops() {
		testObj.playerStands(player);
		actionCounter.incrementAndGet();

	}
	private void dealerStops() {
		testObj.playerStands(dealer);
		actionCounter.incrementAndGet();
	}

	private static Card card(Suite suite, Rank rank) {
		return new Card(suite, rank);
	}

	private void prepareDeckInOrder(Card firstCard, Card... cards) {
		when(deck.draw()).thenReturn(firstCard, cards);
	}
	private void prepareDeckForLongerGame() {
		prepareDeckInOrder(
				card(Suite.CLUB, Rank.TWO)
				, card(Suite.DIAMOND, Rank.TWO)
				, card(Suite.HEART, Rank.TWO)
				, card(Suite.SPADE, Rank.TWO)
				, card(Suite.CLUB, Rank.THREE)
				, card(Suite.DIAMOND, Rank.THREE)
				, card(Suite.HEART, Rank.THREE)
				, card(Suite.SPADE, Rank.THREE)
				, card(Suite.CLUB, Rank.FOUR)
				, card(Suite.DIAMOND, Rank.FOUR)
				, card(Suite.HEART, Rank.FOUR)
				, card(Suite.SPADE, Rank.FOUR)
				, card(Suite.CLUB, Rank.FIVE)
				, card(Suite.DIAMOND, Rank.FIVE)
				, card(Suite.HEART, Rank.FIVE)
				, card(Suite.SPADE, Rank.FIVE)			
				);
	}
	
	private static class EventDispatcherStub implements EventDispatcher {

		private final List<DomainEvent> eventsInChronologicalOrder = Lists.newArrayList();
		
		public void dispatch(DomainEvent event) {
			eventsInChronologicalOrder.add(event);
		}
		
		DomainEvent last() {
			return eventsInChronologicalOrder.get(eventsInChronologicalOrder.size()-1);
		}
		
		DomainEvent get(int order) {
			return eventsInChronologicalOrder.get(order-1);
		}
		
		
		
	}
}
