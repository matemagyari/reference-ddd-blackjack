package org.home.blackjack.app.game;

import javax.inject.Inject;

import org.home.blackjack.app.player.PlayerRecordUpdaterApplicationService;
import org.home.blackjack.domain.common.events.EventBuffer;
import org.home.blackjack.domain.common.events.EventSubscriber;
import org.home.blackjack.domain.common.events.SubscribableEventBus;
import org.home.blackjack.domain.game.Game;
import org.home.blackjack.domain.game.GameRepository;
import org.home.blackjack.domain.game.core.GameID;
import org.home.blackjack.domain.game.event.GameFinishedEvent;
import org.home.blackjack.domain.shared.PlayerID;
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
	
	// private EventStore eventStore;

	public void playerHits(final PlayerID playerID, final GameID gameID) {

		subscribeForGameFinishedEvent(gameID);

		// TODO locking starts for gameID
		Game game = gameRepository.get(gameID);
		game.playerHits(playerID);
		// gameRepository.put(game);
		// TODO locking ends for gameID

		/*
		 * 1. dispatching events must be be outside of the transaction. An Event
		 * might initiate changes in other aggregate instances, and in one
		 * transaction only one aggregate is allowed to change. 2. It also has
		 * to be asynchronous 3. We need subscribers - is subscribing is a
		 * Domain or an Application concept (guess Domain)?
		 */
		// eventStore.flush(gameID);
	}

	public void playerStands(final PlayerID playerID, final GameID gameID) {
		
		subscribeForGameFinishedEvent(gameID);

		// TODO locking starts for gameID
		Game game = gameRepository.get(gameID);
		game.playerStands(playerID);
		// gameRepository.put(game);
		// TODO locking ends for gameID

		/*
		 * 1. dispatching events must be be outside of the transaction. An Event
		 * might initiate changes in other aggregate instances, and in one
		 * transaction only one aggregate is allowed to change. 2. It also has
		 * to be asynchronous 3. We need subscribers - is subscribing is a
		 * Domain or an Application concept (guess Domain)?
		 */
		// eventStore.flush(gameID);
	}
	
	private void subscribeForGameFinishedEvent(final GameID gameID) {
		eventBuffer.register(new EventSubscriber<GameFinishedEvent>() {

			public boolean subscribedTo(GameFinishedEvent event) {
				return event.getGameID().equals(gameID);
			}

			public void handleEvent(GameFinishedEvent event) {
				playerRecordUpdaterApplicationService.playerWon(event.getWinner());
			}
		});
	}
}
