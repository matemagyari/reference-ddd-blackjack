package org.home.blackjack.core.domain.game;

import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang3.Validate;
import org.home.blackjack.core.domain.game.core.Card;
import org.home.blackjack.core.domain.game.core.GameID;
import org.home.blackjack.core.domain.game.event.GameEvent;
import org.home.blackjack.core.domain.game.event.GameFinishedEvent;
import org.home.blackjack.core.domain.game.event.GameStartedEvent;
import org.home.blackjack.core.domain.game.event.InitalCardsDealtEvent;
import org.home.blackjack.core.domain.game.event.PlayerCardDealtEvent;
import org.home.blackjack.core.domain.game.event.PlayerStandsEvent;
import org.home.blackjack.core.domain.game.exception.PlayerActionAfterGameFinishedException;
import org.home.blackjack.core.domain.game.exception.PlayerActionOutOfTurnException;
import org.home.blackjack.core.domain.game.exception.PlayerTriedToActAfterStandException;
import org.home.blackjack.core.domain.shared.PlayerID;
import org.home.blackjack.core.domain.shared.TableID;
import org.home.blackjack.util.ddd.pattern.AggregateRoot;
import org.home.blackjack.util.ddd.pattern.events.DomainEventPublisher;
import org.home.blackjack.util.ddd.util.DomainException;

/**
 * Aggregate Root
 * 
 * Contains entities of {@link Deck} and {@link Player}. The invariants are constraints on the state of the
 * aggregate, that should hold at any point in time (transactional consistency).
 * 
 * The state of the aggregate is the superposition of the states of its entities ({@link Player}-s) and the value of
 * its Value Objects. That's why it can't pass the entities' references away, otherwise their state could be changed
 * outside of the aggregate, breaking the invariants.
 * 
 * Invariant 1. The deck, and the players all together must have 52 unique cards
 * 
 * Invariant 2. If neither player hand stands, then the difference between the number of cards in hands can't be greater
 * than 1
 * 
 * Simplified Blackjack Game for 2 players. For the sake of simplicity, the rules are the simplified versions of the
 * real game
 * 
 * Difference 1: Unlike in real Blackjack, there is no difference between the dealer and the player, apart from 1. in
 * case of draw the dealer wins 2. always the dealer starts
 * 
 * Difference 2: There are only two available actions: 1. hit - draw card 2. stand - stop drawing cards
 * 
 * Difference 3: all cards in initial deal are face down
 * 
 * Difference 4: players can stand any time, regardless of the value of their hands
 * 
 * 
 * What's missing:
 * 
 * 1. GameID - will be calculated by the Repository 2. GameBuilder
 * 
 * 
 * @author Mate
 * 
 */
public class Game extends AggregateRoot<GameID> {

	private static final int TARGET = 21;
	private final Deck deck;
	private final Player dealer;
	private final Player player;
	private final AtomicInteger actionCounter;
	private Player lastToAct;
	private GameState state;
	private TableID tableId;

	/**
	 * The reason why we pass Factories instead of simply passing a {@link Deck} and two {@link Player}-s is because
	 * they are entities, and nothing can hold a reference on them but the aggregate root (so nothing could pass them to
	 * it). Were they Value Objects, that would be another situation.
	 */
	public Game(GameID id, TableID tableId, PlayerID dealerId, PlayerID playerId, DeckFactory deckFactory, DomainEventPublisher domainEventPublisher) {

		super(id, domainEventPublisher);
		Validate.notNull(dealerId);
		Validate.notNull(playerId);
		Validate.notNull(tableId);

		this.tableId = tableId;
		this.deck = deckFactory.createNew();
		this.dealer = Player.createEmptyFor(dealerId);
		this.player = Player.createEmptyFor(playerId);
		this.lastToAct = this.dealer;
		this.state = GameState.BEFORE_INITIAL_DEAL;
		this.actionCounter = new AtomicInteger();
	}
	
	public void dealInitialCards() {
		if (state != GameState.BEFORE_INITIAL_DEAL) {
			throw new IllegalStateException(getID() + " initial deal has been already made");
		}
		publish(new GameStartedEvent(getID(), nextSequenceId(), tableId));
		dealFor(player);
		dealFor(dealer);
		dealFor(player);
		dealFor(dealer);
		publish(new InitalCardsDealtEvent(getID(), nextSequenceId()));
	}

	private void dealFor(Player player) {
		playerHits(player.getID());
	}

	public void playerHits(PlayerID playerId) {
		Player player = handOf(playerId);
		lastToAct = player;
		Card card = deck.draw();
		int score = player.isDealtWith(card);
		PlayerID other = other(playerId).getID();
		publish(new PlayerCardDealtEvent(getID(), nextSequenceId(), playerId, other, card));
		if (score > TARGET) {
			finish(other);
		}
	}

	public void playerStands(PlayerID playerId) {
		Player player = handOf(playerId);
		player.stand();
		publish(new PlayerStandsEvent(getID(), nextSequenceId(), playerId));
		if (lastToAct.stopped()) {
			declareWinner();
		}
		lastToAct = player;
	}

	private void declareWinner() {
		int playerScore = player.score();
		boolean dealerWon = playerScore > TARGET || diffFromTarget(playerScore) > diffFromTarget(dealer.score());
		PlayerID winner = dealerWon ? dealer.getID() : player.getID();
		finish(winner);
	}
	
	private void finish(PlayerID winner) {
		state = GameState.FINISHED;
		publish(new GameFinishedEvent(getID(), tableId, nextSequenceId(), winner, other(winner).getID()));
	}

	private static int diffFromTarget(int score) {
		return Math.abs(TARGET - score);
	}

	private Player handOf(PlayerID playerID) {
		if (dealer.isOf(playerID)) {
			checkValidity(dealer, player);
			return dealer;
		} else if (player.isOf(playerID)) {
			checkValidity(player, dealer);
			return player;
		} else {
			throw new DomainException("Invalid player has tried to act in this Game");
		}

	}

	private void checkValidity(Player playerTryingToAct, Player otherPlayer) {
		if (playerTryingToAct.equals(lastToAct) && otherPlayer.notStopped()) {
			throw new PlayerActionOutOfTurnException(playerTryingToAct.getID());
		} else if (isFinished()) {
			throw new PlayerActionAfterGameFinishedException(playerTryingToAct.getID());
		} else if (playerTryingToAct.stopped()) {
			throw new PlayerTriedToActAfterStandException(playerTryingToAct.getID());
		}

	}
	
	private void publish(GameEvent event) {
		domainEventPublisher().publish(event);
	}

	private Player other(PlayerID player) {
		return this.player.getID().equals(player) ? dealer : this.player;
	}

	public boolean isFinished() {
		return state == GameState.FINISHED;
	}

	private int nextSequenceId() {
		return actionCounter.getAndIncrement();
	}

	private static enum GameState {
		BEFORE_INITIAL_DEAL,
		AFTER_INITIAL_DEAL,
		FINISHED
	}

	@Override
	public String toString() {
		return "Game [dealer=" + dealer + ", player=" + player + ", actionCounter=" + actionCounter + ", lastToAct=" + lastToAct
				+ ", state=" + state + ", deck=" + deck + "]";
	}
	
}
