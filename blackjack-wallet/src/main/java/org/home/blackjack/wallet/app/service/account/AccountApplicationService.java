package org.home.blackjack.wallet.app.service.account;

import javax.annotation.Resource;
import javax.inject.Named;

import org.home.blackjack.util.marker.hexagonal.DrivenPort;
import org.home.blackjack.wallet.domain.wallet.CashAmount;
import org.home.blackjack.wallet.domain.wallet.Currency;
import org.home.blackjack.wallet.domain.wallet.Wallet;
import org.home.blackjack.wallet.domain.wallet.WalletId;
import org.home.blackjack.wallet.domain.wallet.WalletRepository;

@Named
public class AccountApplicationService implements DrivenPort {
    
    @Resource
    private WalletRepository walletRepository;
    
    public CashAmount getBalance(WalletId walletId) {
        Wallet wallet = walletRepository.find(walletId);
        return wallet != null ? wallet.amount() : CashAmount.zero(Currency.CHIPS);
    }

    // for sake of simplicity, no uniqueness-check
    public void createAccount(WalletId walletId, CashAmount startingBalance) {
        Wallet wallet = new Wallet(walletId, startingBalance);
        walletRepository.create(wallet);
    }
}
