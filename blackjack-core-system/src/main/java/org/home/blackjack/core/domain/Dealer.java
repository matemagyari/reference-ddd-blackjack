package org.home.blackjack.core.domain;

import java.util.List;

import javax.annotation.Resource;
import javax.inject.Named;

import org.home.blackjack.core.domain.cashier.Cashier;
import org.home.blackjack.core.domain.game.Game;
import org.home.blackjack.core.domain.game.GameFactory;
import org.home.blackjack.core.domain.game.GameRepository;
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
    @Resource
    private Cashier cashier;

	public void startANewGameOnTable(TableID tableId, List<PlayerID> players) {
		for (PlayerID playerID : players) {
			cashier.debitEntryFee(playerID);
		}
		Game newGame = gameFactory.createNewGame(tableId, players);
		newGame.dealInitialCards();
		gameRepository.create(newGame);
	}

}
