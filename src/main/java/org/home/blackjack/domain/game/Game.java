package org.home.blackjack.domain.game;

import java.util.concurrent.atomic.AtomicInteger;

import javax.inject.Inject;

import org.apache.commons.lang3.Validate;
import org.home.blackjack.domain.AggregateRoot;
import org.home.blackjack.domain.common.DomainException;
import org.home.blackjack.domain.common.EventBus;
import org.home.blackjack.domain.game.event.GameFinishedEvent;
import org.home.blackjack.domain.game.event.InitalCardsDealtEvent;
import org.home.blackjack.domain.game.event.PlayerCardDealtEvent;
import org.home.blackjack.domain.game.event.PlayerStandsEvent;
import org.home.blackjack.domain.game.exception.PlayerActionAfterGameFinishedException;
import org.home.blackjack.domain.game.exception.PlayerActionOutOfTurnException;
import org.home.blackjack.domain.game.exception.PlayerTriedToActAfterStandException;
import org.home.blackjack.domain.player.PlayerID;

/**
 * Aggregate Root
 * 
 * Contains entities of {@link Deck} and {@link PlayerHand}. The invariants are constraints on the state of the
 * aggregate, that should hold at any point in time (transactional consistency).
 * 
 * The state of the aggregate is the superposition of the states of its entities ({@link PlayerHand}-s) and the value of
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
	private final PlayerHand dealerHand;
	private final PlayerHand playerHand;
	private final AtomicInteger actionCounter;
	private PlayerHand lastToAct;
	private GameState state;

	@Inject
	private static EventBus eventBus;

	/**
	 * The reason why we pass Factories instead of simply passing a {@link Deck} and two {@link PlayerHand}-s is because
	 * they are entities, and nothing can hold a reference on them but the aggregate root (so nothing could pass them to
	 * it). Were they Value Objects, that would be another situation.
	 * 
	 */
	public Game(GameID id, PlayerID dealer, PlayerID player, DeckFactory deckFactory) {

		super(id);
		Validate.notNull(dealer);
		Validate.notNull(player);

		this.deck = deckFactory.createNew();
		this.dealerHand = PlayerHand.createEmptyFor(dealer);
		this.playerHand = PlayerHand.createEmptyFor(player);
		this.lastToAct = this.dealerHand;
		this.state = GameState.BEFORE_INITIAL_DEAL;
		this.actionCounter = new AtomicInteger();
	}

	public void dealInitialCards() {
		if (state != GameState.BEFORE_INITIAL_DEAL) {
			throw new IllegalStateException(getID() + " initial deal has been already made");
		}
		dealFor(playerHand);
		dealFor(dealerHand);
		dealFor(playerHand);
		dealFor(dealerHand);
		eventBus.publish(new InitalCardsDealtEvent(getID(), actionCounter.get()));
	}

	private void dealFor(PlayerHand hand) {
		playerHits(hand.getPlayerID());

	}

	public void playerHits(PlayerID player) {
		PlayerHand hand = handOf(player);
		lastToAct = hand;
		Card card = deck.draw();
		int score = hand.isDealtWith(card);
		eventBus.publish(new PlayerCardDealtEvent(getID(), nextSequenceId(), player, other(player).getPlayerID(), card));
		if (score > TARGET) {
			state = GameState.FINISHED;
			eventBus.publish(new GameFinishedEvent(getID(), nextSequenceId(), other(player).getPlayerID()));
		}
	}

	public void playerStands(PlayerID player) {
		PlayerHand hand = handOf(player);
		hand.stand();
		eventBus.publish(new PlayerStandsEvent(getID(), nextSequenceId(), player));
		if (lastToAct.stopped()) {
			state = GameState.FINISHED;
			declareWinner();
		}
		lastToAct = hand;
	}

	private void declareWinner() {
		int playerScore = playerHand.score();
		boolean dealerWon = playerScore > TARGET || diffFromTarget(playerScore) > diffFromTarget(dealerHand.score());
		PlayerID winner = dealerWon ? dealerHand.getPlayerID() : playerHand.getPlayerID();
		eventBus.publish(new GameFinishedEvent(getID(), nextSequenceId(), winner));
	}

	private static int diffFromTarget(int score) {
		return Math.abs(TARGET - score);
	}

	private PlayerHand handOf(PlayerID playerID) {
		if (dealerHand.isOf(playerID)) {
			checkValidity(dealerHand, playerHand);
			return dealerHand;
		} else if (playerHand.isOf(playerID)) {
			checkValidity(playerHand, dealerHand);
			return playerHand;
		} else {
			throw new DomainException("Invalid player has tried to act in this Game");
		}

	}

	private void checkValidity(PlayerHand playerTryingToAct, PlayerHand otherPlayer) {
		if (playerTryingToAct.equals(lastToAct) && otherPlayer.notStopped()) {
			throw new PlayerActionOutOfTurnException(playerTryingToAct.getPlayerID());
		} else if (isFinished()) {
			throw new PlayerActionAfterGameFinishedException(playerTryingToAct.getPlayerID());
		} else if (playerTryingToAct.stopped()) {
			throw new PlayerTriedToActAfterStandException(playerTryingToAct.getPlayerID());
		}

	}

	private PlayerHand other(PlayerID player) {
		return playerHand.getPlayerID().equals(player) ? dealerHand : playerHand;
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
}
