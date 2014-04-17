package org.home.blackjack.core.domain.game;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

import org.home.blackjack.core.EventPublisherStub;
import org.home.blackjack.core.ReflectionHelper;
import org.home.blackjack.core.domain.game.Deck;
import org.home.blackjack.core.domain.game.DeckFactory;
import org.home.blackjack.core.domain.game.Game;
import org.home.blackjack.core.domain.game.core.Card;
import org.home.blackjack.core.domain.game.core.GameID;
import org.home.blackjack.core.domain.game.core.Card.Rank;
import org.home.blackjack.core.domain.game.core.Card.Suite;
import org.home.blackjack.core.domain.game.event.GameFinishedEvent;
import org.home.blackjack.core.domain.game.exception.PlayerActionOutOfTurnException;
import org.home.blackjack.core.domain.game.exception.PlayerTriedToActAfterStandException;
import org.home.blackjack.core.domain.shared.PlayerID;
import org.home.blackjack.core.domain.shared.TableID;
import org.home.blackjack.core.infrastructure.JUGIDGenerationStrategy;
import org.home.blackjack.util.ddd.pattern.domain.ID;
import org.home.blackjack.util.ddd.pattern.domain.IDGenerationStrategy;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * This is not a unit test, but testing the Game mechanism as a whole. It tests the Game mechanism, and check the
 * events.
 * 
 * @author Mate
 * 
 */
@RunWith(MockitoJUnitRunner.class)
public class GameTest {

	@Mock
	private Deck deck;
	@Mock
	private DeckFactory deckFactory;

	private EventPublisherStub eventBus = new EventPublisherStub();

	private Game testObj;

	@Mock
	private IDGenerationStrategy idGenerationStrategy;

	private PlayerID dealer = GameFixture.aPlayerID();
	private PlayerID player = GameFixture.aPlayerID();
	private GameID gameID = GameFixture.aGameID();
	private TableID tableID = GameFixture.aTableID();

	private AtomicInteger actionCounter = new AtomicInteger();

	@BeforeClass
	public static void setUpStatic() throws NoSuchFieldException, IllegalAccessException {

		ReflectionHelper.setField("idGenerationStrategy", new JUGIDGenerationStrategy(), ID.class);
	}

	@Before
	public void setUp() {

		when(deckFactory.createNew()).thenReturn(deck);
		testObj = new Game(gameID,tableID, dealer, player, deckFactory, eventBus);
	}

	@After
	public void tearDown() throws IOException, ClassNotFoundException {
		eventBus.print();
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
	public void playerWinsWithBlackJack() throws IOException, ClassNotFoundException {
		prepareDeckInOrder(
				card(Suite.CLUB, Rank.ACE)
				,card(Suite.SPADE, Rank.KING)
				,card(Suite.DIAMOND, Rank.TEN)
				,card(Suite.HEART, Rank.TEN));
		dealInitialCards();
		playerStops();
		dealerStops();
		assertTheWinnerIs(player, dealer);
	}

	@Test
	public void dealerWinsWithBlackJack() throws IOException, ClassNotFoundException {
		prepareDeckInOrder(
				card(Suite.CLUB, Rank.KING)
				,card(Suite.SPADE, Rank.ACE)
				,card(Suite.DIAMOND, Rank.TEN)
				,card(Suite.HEART, Rank.JACK));
		dealInitialCards();
		playerStops();
		dealerStops();
		assertTheWinnerIs(dealer, player);
	}

	@Test
	public void playerWinsAfterSeveralHitsWith18Against17() throws IOException, ClassNotFoundException {
		prepareDeckInOrder(
		        card(Suite.CLUB, Rank.TWO)
		        , card(Suite.SPADE, Rank.TWO)
		        , card(Suite.DIAMOND, Rank.SEVEN)
		        , card(Suite.HEART, Rank.JACK)
				, card(Suite.DIAMOND, Rank.FIVE)
				, card(Suite.HEART, Rank.THREE)
				, card(Suite.DIAMOND, Rank.FOUR)
				, card(Suite.HEART, Rank.TWO));
		dealInitialCards();
		playerDraws();
		dealerDraws();
		playerDraws();
		dealerDraws();
		playerStops();
		dealerStops();
		assertTheWinnerIs(player, dealer);
	}

	@Test
	public void playerBusts() throws IOException, ClassNotFoundException {
		prepareDeckInOrder(
		        card(Suite.CLUB, Rank.TEN)
		        , card(Suite.SPADE, Rank.TWO)
		        , card(Suite.DIAMOND, Rank.SEVEN)
		        , card(Suite.HEART, Rank.JACK)
				, card(Suite.DIAMOND, Rank.FIVE));
		dealInitialCards();
		playerDraws();
		assertTheWinnerIs(dealer, player);
	}

	@Test
	public void dealerBusts() throws IOException, ClassNotFoundException {
		prepareDeckInOrder(
		        card(Suite.CLUB, Rank.TWO)
		        , card(Suite.SPADE, Rank.EIGHT)
		        , card(Suite.DIAMOND, Rank.SEVEN)
		        , card(Suite.HEART, Rank.JACK)
				, card(Suite.DIAMOND, Rank.THREE)
				, card(Suite.DIAMOND, Rank.FOUR));
		dealInitialCards();
		playerDraws();
		dealerDraws();
		assertTheWinnerIs(player, dealer);
	}

	private void assertTheWinnerIs(PlayerID winner, PlayerID loser) throws IOException, ClassNotFoundException {
		assertTrue(testObj.isFinished());
		eventBus.print();
		assertEquals(new GameFinishedEvent(gameID,tableID, actionCounter.get(), winner, loser), eventBus.last());
	}

	private void dealInitialCards() {
		testObj.dealInitialCards();
		//four cards dealt
		actionCounter.incrementAndGet();
		actionCounter.incrementAndGet();
		actionCounter.incrementAndGet();
		actionCounter.incrementAndGet();
		//two events dispatched
		actionCounter.incrementAndGet();
		actionCounter.incrementAndGet();
	}

	private void playerDraws() {
		hits(player, dealer);
	}

	private void dealerDraws() {
		hits(dealer, player);
	}

	private void hits(PlayerID playerToAct, PlayerID otherPlayer) {
		try {
			testObj.playerHits(playerToAct);
			actionCounter.incrementAndGet();
			/*
			 * PlayerCardDealtEvent domainEvent = (PlayerCardDealtEvent) eventDispatcher.get(actionCounter.get()-1);
			 * assertEquals(gameID, domainEvent.getGameID()); assertEquals(actionCounter.get(),
			 * domainEvent.getSequenceNumber()); assertEquals(playerToAct, domainEvent.getPlayer());
			 * assertEquals(otherPlayer, domainEvent.getOtherPlayer());
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

		prepareDeckInOrder(card(Suite.CLUB, Rank.TWO), card(Suite.DIAMOND, Rank.TWO), card(Suite.HEART, Rank.TWO),
				card(Suite.SPADE, Rank.TWO), card(Suite.CLUB, Rank.THREE), card(Suite.DIAMOND, Rank.THREE),
				card(Suite.HEART, Rank.THREE), card(Suite.SPADE, Rank.THREE), card(Suite.CLUB, Rank.FOUR),
				card(Suite.DIAMOND, Rank.FOUR), card(Suite.HEART, Rank.FOUR), card(Suite.SPADE, Rank.FOUR),
				card(Suite.CLUB, Rank.FIVE), card(Suite.DIAMOND, Rank.FIVE), card(Suite.HEART, Rank.FIVE),
				card(Suite.SPADE, Rank.FIVE));
	}
}
