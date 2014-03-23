package org.home.blackjack.core.domain.cashier;

import org.home.blackjack.core.domain.shared.PlayerID;
import org.home.blackjack.util.marker.hexagonal.DrivingPort;

/**
 * Domain Service. Separated Interface. Stands in front of the ACL.
 *
 */
public interface WalletService extends DrivingPort {

	void credit(PlayerID player, Integer amount);
	void debit(PlayerID playerID, Integer amount);
	void createAccount(PlayerID playerID, Integer startBalance);

}
