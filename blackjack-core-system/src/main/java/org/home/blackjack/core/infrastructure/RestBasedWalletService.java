package org.home.blackjack.core.infrastructure;

import javax.inject.Named;

import org.home.blackjack.core.domain.shared.PlayerID;
import org.home.blackjack.core.domain.wallet.Reason;
import org.home.blackjack.core.domain.wallet.WalletService;

@Named
public class RestBasedWalletService implements WalletService {

	@Override
	public void giveTheWin(PlayerID winner, Reason reason) {
	}

	@Override
	public void takeTheLoss(PlayerID loser, Reason reason) {
	}

}
