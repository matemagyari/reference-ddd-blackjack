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
import org.home.blackjack.util.locking.aspect.LockVal;
import org.home.blackjack.util.locking.aspect.WithPessimisticLock;
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
public class GameActionApplicationServiceImpl implements GameActionApplicationService {

	@Resource
	private GameRepository gameRepository;
	@Resource
	private GameEventHandler gameEventHandler;
	@Resource
	private GameFinishedEventHandler gameFinishedEventHandler;

	@WithPessimisticLock(repository = GameRepository.class)
	@Override
	public void handlePlayerAction(final GameID gameID, final GameAction gameAction) {

		SubscribableEventBus eventBus = LightweightDomainEventBus.subscribableEventBusInstance();
		eventBus.reset();
		eventBus.register(gameEventHandler, gameFinishedEventHandler);

		performTransaction(gameAction);

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
