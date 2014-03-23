package org.home.blackjack.core.app.service.game;

import javax.annotation.Resource;
import javax.inject.Named;

import org.home.blackjack.core.domain.game.Game;
import org.home.blackjack.core.domain.game.GameRepository;
import org.home.blackjack.util.locking.aspect.WithPessimisticLock;

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

    @Override
    @WithPessimisticLock(repository=GameRepository.class, lockMethod="getGameID")
	public void handlePlayerAction(GameCommand command) {
        Game game = gameRepository.find(command.getGameID());
        if (command.getAction() == GameActionType.HIT) {
            game.playerHits(command.getPlayerId());
        } else if (command.getAction() == GameActionType.STAND) {
            game.playerStands(command.getPlayerId());
        }
        gameRepository.update(game);	}

}
