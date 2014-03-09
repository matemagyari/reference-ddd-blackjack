package org.home.blackjack.core.domain.cashier;

import javax.annotation.Resource;
import javax.inject.Named;

import org.home.blackjack.core.domain.game.core.GameID;
import org.home.blackjack.core.domain.shared.PlayerID;

/**
 * Domain Service
 *
 */
@Named
public class Cashier {

	private static Integer startingBalance = 500;
	private static Integer entryFee = 500;

	@Resource
	private WalletService walletService;

	public void debitEntryFee(PlayerID playerID) {
		walletService.debit(playerID, entryFee);
	}

	// TODO mmagyari - should this class know how many players were? probably
	// better to pass it in an argument
	public void giveTheWin(GameID gameID, PlayerID winner) {
		walletService.credit(winner, entryFee * 2);
	}

	public void createAccount(PlayerID playerID) {
		walletService.createAccount(playerID, startingBalance);
	}

}
