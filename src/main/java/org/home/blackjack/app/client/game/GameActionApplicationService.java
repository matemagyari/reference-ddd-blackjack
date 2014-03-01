package org.home.blackjack.app.client.game;

import javax.annotation.Resource;
import javax.inject.Named;

import org.home.blackjack.app.eventhandler.GameEventHandler;
import org.home.blackjack.app.eventhandler.GameFinishedEventHandler;
import org.home.blackjack.domain.game.Game;
import org.home.blackjack.domain.game.GameRepository;
import org.home.blackjack.domain.game.core.GameID;
import org.home.blackjack.util.ddd.pattern.events.LightweightDomainEventBus;
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
@Named
public class GameActionApplicationService implements DrivenPort {

	@Resource
	private GameRepository gameRepository;
	@Resource
	private FinegrainedLockable<GameID> lockableGameRepository;
	@Resource
	private GameEventHandler gameEventHandler;
	@Resource
	private GameFinishedEventHandler gameFinishedEventHandler;

	private final LockTemplate lockTemplate = new LockTemplate();

	public void handlePlayerAction(final GameAction gameAction) {

		SubscribableEventBus eventBus = LightweightDomainEventBus.subscribableEventBusInstance();
		eventBus.reset();
		eventBus.register(gameEventHandler);
		eventBus.register(gameFinishedEventHandler);

		lockTemplate.doWithLock(lockableGameRepository, gameAction.getGameID(), new VoidWriteLockingAction<GameID>() {
			@Override
			public void withWriteLock(GameID key) {
				performTransaction(gameAction);
			}
		});

		eventBus.flush();

	}

	private void performTransaction(GameAction gameAction) {
		Game game = gameRepository.find(gameAction.getGameID());
		if (gameAction.getGameActionType() == GameActionType.HIT) {
			game.playerHits(gameAction.getPlayerID());
		} else if (gameAction.getGameActionType() == GameActionType.STAND) {
			game.playerStands(gameAction.getPlayerID());
		}
		gameRepository.update(game);
	}

}
