package org.home.blackjack.wallet.domain.wallet;

import org.home.blackjack.util.ddd.pattern.domain.Repository;

public interface WalletRepository extends Repository<WalletId, Wallet> {

	Wallet find(WalletId walletId);
	void update(Wallet wallet);
	void create(Wallet wallet);
}
