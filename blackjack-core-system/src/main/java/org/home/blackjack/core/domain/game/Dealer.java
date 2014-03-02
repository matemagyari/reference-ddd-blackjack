package org.home.blackjack.core.domain.game;

import java.util.List;

import javax.annotation.Resource;
import javax.inject.Named;

import org.home.blackjack.core.domain.shared.PlayerID;
import org.home.blackjack.core.domain.shared.TableID;

/**
 * Domain Service
 *
 */
@Named
public class Dealer {
	
    @Resource
    private GameRepository gameRepository;
    @Resource
    private GameFactory gameFactory;

	public void startANewGameOnTable(TableID tableId, List<PlayerID> players) {
		Game newGame = gameFactory.createNewGame(tableId, players);
		newGame.dealInitialCards();
		gameRepository.create(newGame);
	}

}
