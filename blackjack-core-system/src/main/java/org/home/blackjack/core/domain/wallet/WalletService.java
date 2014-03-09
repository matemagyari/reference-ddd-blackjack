package org.home.blackjack.core.domain.wallet;

import org.home.blackjack.core.domain.game.core.GameID;
import org.home.blackjack.core.domain.shared.PlayerID;

/**
 * Domain Service. Separated Interface. Stands in front of the ACL.
 *
 */
public interface WalletService {

	void giveTheWin(GameID gameID, PlayerID winner);
	void debitEntryFee(PlayerID playerID);
	void createAccount(PlayerID playerID, int startBalance);

}
