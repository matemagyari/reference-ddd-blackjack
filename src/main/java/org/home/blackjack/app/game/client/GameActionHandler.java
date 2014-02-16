package org.home.blackjack.app.game.client;

import org.home.blackjack.domain.coreservice.EventStore;
import org.home.blackjack.domain.game.Game;
import org.home.blackjack.domain.game.GameRepository;
import org.home.blackjack.util.hexagonal.DrivenPort;

/**
 * Driven port.
 * App service for player action use-case.
 * 
 * The EventStore should be flushed only after the aggregate has been persisted.
 * 
 * @author Mate
 * 
 */
public class GameActionHandler implements DrivenPort {

	private GameRepository gameRepository;
	private EventStore eventStore;

	public void handleAction(GameAction action) {

		// TODO locking starts for gameId
		Game game = gameRepository.find(action.getGameId());
		if (GameActionType.HIT == action.getType()) {
			game.playerHits(action.getPlayer());
		} else if (GameActionType.STAND == action.getType()) {
			game.playerStands(action.getPlayer());
		} else {
			throw new IllegalArgumentException("Unknown action type " + action.getType());
		}
		
		gameRepository.update(game);
		eventStore.flush();
		// TODO locking ends for gameId

	}

}
