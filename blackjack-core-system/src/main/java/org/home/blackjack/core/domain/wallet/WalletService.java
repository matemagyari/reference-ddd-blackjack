package org.home.blackjack.core.domain.wallet;

import org.home.blackjack.core.domain.shared.PlayerID;

/**
 * Domain Service. Separated Interface. Stands in front of the ACL.
 *
 */
public interface WalletService {

	void giveTheWin(PlayerID winner, Reason reason);
	void takeTheLoss(PlayerID loser, Reason reason);

}
