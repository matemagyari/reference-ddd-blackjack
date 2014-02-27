package org.home.blackjack.domain.game;

import java.util.List;

import javax.inject.Inject;

import org.home.blackjack.domain.shared.PlayerID;
import org.home.blackjack.domain.table.core.TableID;

/**
 * Domain Service
 *
 */
public class Dealer {
	
    @Inject
    private GameRepository gameRepository;
    @Inject
    private GameFactory gameFactory;

	public void startANewGameOnTable(TableID tableId, List<PlayerID> players) {
		Game newGame = gameFactory.createNewGame(tableId, players);
		newGame.dealInitialCards();
		gameRepository.create(newGame);
	}

}
