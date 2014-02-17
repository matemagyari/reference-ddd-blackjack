package org.home.blackjack.app.game;

import javax.inject.Inject;

import org.home.blackjack.domain.game.Game;
import org.home.blackjack.domain.game.GameRepository;
import org.home.blackjack.domain.game.core.GameID;
import org.home.blackjack.domain.shared.PlayerID;
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

	// private EventStore eventStore;

	public void playerHits(final PlayerID playerID, final GameID gameID) {

		// TODO locking starts for gameID
		Game game = gameRepository.get(gameID);
		game.playerHits(playerID);
		// gameRepository.put(game);
		// TODO locking ends for gameID

		/*
		 * 1. dispatching events must be be outside of the transaction. An Event might initiate changes in other
		 * aggregate instances, and in one transaction only one aggregate is allowed to change. 2. It also has to be
		 * asynchronous 3. We need subscribers - is subscribing is a Domain or an Application concept (guess Domain)?
		 */
		// eventStore.flush(gameID);
	}

	public void playerStands(final PlayerID playerID, final GameID gameID) {

		// TODO locking starts for gameID
		Game game = gameRepository.get(gameID);
		game.playerStands(playerID);
		// gameRepository.put(game);
		// TODO locking ends for gameID

		/*
		 * 1. dispatching events must be be outside of the transaction. An Event might initiate changes in other
		 * aggregate instances, and in one transaction only one aggregate is allowed to change. 2. It also has to be
		 * asynchronous 3. We need subscribers - is subscribing is a Domain or an Application concept (guess Domain)?
		 */
		// eventStore.flush(gameID);
	}
}
