package org.home.blackjack.app.game.client;

import org.home.blackjack.domain.common.EventStore;
import org.home.blackjack.domain.game.Game;
import org.home.blackjack.domain.game.GameRepository;
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
        // TODO locking ends for gameId

        /*
         * 1. dispatching events must be be outside of the transaction. An Event might initiate
         * changes in other aggregate instances, and in one transaction only one
         * aggregate is allowed to change.
         * 2. It also has to be asynchronous
         * 3. We need subscribers - is subscribing is a Domain or an Application concept (guess Domain)?
         */
         
        eventStore.flush(action.getGameId());
    }
}
