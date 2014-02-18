package org.home.blackjack.app.game;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Inject;

import org.home.blackjack.app.player.PlayerRecordUpdaterApplicationService;
import org.home.blackjack.domain.common.events.EventSubscriber;
import org.home.blackjack.domain.common.events.SubscribableEventBus;
import org.home.blackjack.domain.game.Game;
import org.home.blackjack.domain.game.GameRepository;
import org.home.blackjack.domain.game.core.GameID;
import org.home.blackjack.domain.game.event.GameFinishedEvent;
import org.home.blackjack.util.ddd.pattern.DomainEvent;
import org.home.blackjack.util.marker.hexagonal.DrivenPort;

/**
 * Driven port. App service for player action use-case.
 * 
 * The EventStore should be flushed only after the aggregate has been persisted.
 * 
 * @author Mate
 * 
 */
public class GameActionApplicationService implements DrivenPort {

	@Inject
	private GameRepository gameRepository;
	@Inject
	private SubscribableEventBus eventBuffer;
	@Inject
	private PlayerRecordUpdaterApplicationService playerRecordUpdaterApplicationService;

	public void handlePlayerAction(GameAction gameAction) {

		subscribeForGameFinishedEvent();

		// TODO locking starts for gameID
		Game game = gameRepository.get(gameAction.getGameID());
		if (gameAction.getGameActionType() == GameActionType.HIT) {
			game.playerHits(gameAction.getPlayerID());
		} else if (gameAction.getGameActionType() == GameActionType.STAND) {
			game.playerHits(gameAction.getPlayerID());
		}
		gameRepository.put(game);
		// TODO locking ends for gameID
		eventBuffer.flush();

		/*
		 * 1. dispatching events must be be outside of the transaction. An Event
		 * might initiate changes in other aggregate instances, and in one
		 * transaction only one aggregate is allowed to change. 2. It also has
		 * to be asynchronous 3. We need subscribers - is subscribing is a
		 * Domain or an Application concept (guess Domain)?
		 */
	}

	private void subscribeForGameFinishedEvent() {
		eventBuffer.register(new EventSubscriber<GameFinishedEvent>() {

			public boolean subscribedTo(DomainEvent event) {
				return event instanceof GameFinishedEvent;
			}

			public void handleEvent(GameFinishedEvent event) {
				playerRecordUpdaterApplicationService.playerWon(event.getWinner());
			}
		});
	}
}
