package org.home.blackjack.app.client.game;


import javax.inject.Inject;

import org.home.blackjack.app.eventhandler.GameEventHandler;
import org.home.blackjack.app.eventhandler.GameFinishedEventHandler;
import org.home.blackjack.domain.game.Game;
import org.home.blackjack.domain.game.GameRepository;
import org.home.blackjack.domain.game.core.GameID;
import org.home.blackjack.util.ddd.pattern.events.SubscribableEventBus;
import org.home.blackjack.util.locking.FinegrainedLockable;
import org.home.blackjack.util.locking.LockTemplate;
import org.home.blackjack.util.locking.VoidWriteLockingAction;
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
	private FinegrainedLockable<GameID> lockableGameRepository;
	@Inject
	private GameEventHandler gameEventHandler;
	@Inject
	private GameFinishedEventHandler gameFinishedEventHandler;
	
	private final LockTemplate lockTemplate = new LockTemplate();

	public void handlePlayerAction(final GameAction gameAction) {

	    eventBuffer.register(gameEventHandler);
	    eventBuffer.register(gameFinishedEventHandler);
		
		lockTemplate.doWithLock(lockableGameRepository, gameAction.getGameID(),  new VoidWriteLockingAction<GameID>() {
            @Override
            public void withWriteLock(GameID key) {
                performTransaction(gameAction);
            }
        } );

		eventBuffer.flush();
	}

    private void performTransaction(GameAction gameAction) {
        Game game = gameRepository.find(gameAction.getGameID());
		if (gameAction.getGameActionType() == GameActionType.HIT) {
			game.playerHits(gameAction.getPlayerID());
		} else if (gameAction.getGameActionType() == GameActionType.STAND) {
			game.playerHits(gameAction.getPlayerID());
		}
		gameRepository.update(game);
    }
	
}
