package org.home.blackjack.wallet.infrastructure.persistence.wallet;

import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.inject.Named;

import org.home.blackjack.util.locking.FinegrainedLockable;
import org.home.blackjack.wallet.domain.wallet.Wallet;
import org.home.blackjack.wallet.domain.wallet.WalletId;
import org.home.blackjack.wallet.domain.wallet.WalletRepository;

import com.google.common.collect.Maps;

@Named
public class InMemoryWalletRespository implements WalletRepository, FinegrainedLockable<WalletId> {
	
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

    @Override
    public Lock getLockForKey(WalletId key) {
        locks.putIfAbsent(key, new ReentrantLock());
        return locks.get(key);
    }

}
