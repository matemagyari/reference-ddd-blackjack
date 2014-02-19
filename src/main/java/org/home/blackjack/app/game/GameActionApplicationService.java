package org.home.blackjack.app.game;


import javax.inject.Inject;

import org.home.blackjack.app.player.PlayerRecordUpdaterApplicationService;
import org.home.blackjack.domain.common.events.EventSubscriber;
import org.home.blackjack.domain.common.events.SubscribableEventBus;
import org.home.blackjack.domain.game.Game;
import org.home.blackjack.domain.game.GameRepository;
import org.home.blackjack.domain.game.core.GameID;
import org.home.blackjack.domain.game.event.GameFinishedEvent;
import org.home.blackjack.util.ddd.pattern.DomainEvent;
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
	private PlayerRecordUpdaterApplicationService playerRecordUpdaterApplicationService;
	@Inject
	private FinegrainedLockable<GameID> lockableGameRepository;
	
	private final LockTemplate lockTemplate = new LockTemplate();

	public void handlePlayerAction(final GameAction gameAction) {

		subscribeForGameFinishedEvent();
		
		lockTemplate.doWithLock(lockableGameRepository, gameAction.getGameID(),  new VoidWriteLockingAction<GameID>() {
            @Override
            public void withWriteLock(GameID key) {
                performTransaction(gameAction);
            }
        } );

		eventBuffer.flush();
	}

    private void performTransaction(GameAction gameAction) {
        Game game = gameRepository.get(gameAction.getGameID());
		if (gameAction.getGameActionType() == GameActionType.HIT) {
			game.playerHits(gameAction.getPlayerID());
		} else if (gameAction.getGameActionType() == GameActionType.STAND) {
			game.playerHits(gameAction.getPlayerID());
		}
		gameRepository.put(game);
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
