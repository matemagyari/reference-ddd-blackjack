package org.home.blackjack.app.game;


import javax.inject.Inject;

import org.home.blackjack.app.event.ExternalEventPublisher;
import org.home.blackjack.app.player.PlayerRecordUpdaterApplicationService;
import org.home.blackjack.domain.common.events.EventSubscriber;
import org.home.blackjack.domain.common.events.SubscribableEventBus;
import org.home.blackjack.domain.game.Game;
import org.home.blackjack.domain.game.GameRepository;
import org.home.blackjack.domain.game.core.GameID;
import org.home.blackjack.domain.game.event.GameEvent;
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
	@Inject
	private ExternalEventPublisher externalEventPublisher;
	
	
	
	private final LockTemplate lockTemplate = new LockTemplate();

	public void handlePlayerAction(final GameAction gameAction) {

		subscribeForGameFinishedEvent();
		subscribeForGeneralGameEvent();
		
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
	
	//this events go outside of the Bounded Context
	private void subscribeForGeneralGameEvent() {
		eventBuffer.register(new EventSubscriber<GameEvent>() {

			@Override
			public boolean subscribedTo(DomainEvent event) {
				return event instanceof GameEvent;
			}

			@Override
			public void handleEvent(GameEvent event) {
				externalEventPublisher.publish(event);
			}

		});
	}
}
