package org.home.blackjack.domain.game;

import java.util.List;

import javax.annotation.Resource;
import javax.inject.Named;

import org.home.blackjack.domain.shared.PlayerID;
import org.home.blackjack.domain.table.core.TableID;

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
