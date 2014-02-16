package org.home.blackjack.app.service;

import org.home.blackjack.domain.event.EventStore;
import org.home.blackjack.domain.game.Game;
import org.home.blackjack.domain.game.GameRepository;

/**
 * App service for player action use-case.
 * 
 * The EventStore should be flushed only after the aggregate has been persisted.
 * 
 * @author Mate
 * 
 */
public class GameActionHandler {

	private GameRepository gameRepository;
	private EventStore eventStore;

	public void handleAction(GameAction action) {

		// TODO locking starts for gameId
		Game game = gameRepository.find(action.getGameId());
		if (GameActionType.HIT == action.getActionType()) {
			game.playerHits(action.getPlayer());
		} else if (GameActionType.STAND == action.getActionType()) {
			game.playerStands(action.getPlayer());
		}
		
		gameRepository.update(game);
		eventStore.flush();
		// TODO locking ends for gameId

	}

}
