package org.home.blackjack.wallet.domain.wallet;

import org.home.blackjack.util.ddd.pattern.domain.model.ID;

public class WalletId extends ID {

	private WalletId(String walletId) {
		super(walletId);
	}

	public static WalletId createFrom(String walletId) {
		return new WalletId(walletId);
	}

}
