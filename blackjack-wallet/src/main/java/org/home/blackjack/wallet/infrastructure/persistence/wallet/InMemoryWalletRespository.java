package org.home.blackjack.wallet.infrastructure.persistence.wallet;

import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.Lock;

import javax.inject.Named;

import org.home.blackjack.wallet.domain.wallet.Wallet;
import org.home.blackjack.wallet.domain.wallet.WalletId;
import org.home.blackjack.wallet.domain.wallet.WalletRepository;

import com.google.common.collect.Maps;

@Named
public class InMemoryWalletRespository implements WalletRepository {
	
	private final Map<WalletId, Wallet> map = Maps.newHashMap();
	private final ConcurrentMap<WalletId, Lock> locks = Maps.newConcurrentMap();

	@Override
	public Wallet find(WalletId walletId) {
		return map.get(walletId);
	}

	@Override
	public void update(Wallet wallet) {
		map.put(wallet.getID(), wallet);
	}

	@Override
	public void create(Wallet wallet) {
		map.put(wallet.getID(), wallet);
	}

}
