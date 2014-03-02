package org.home.blackjack.wallet.app.client.transaction;

import javax.annotation.Resource;
import javax.inject.Named;

import org.home.blackjack.util.locking.aspect.WithPessimisticLock;
import org.home.blackjack.wallet.domain.transaction.TransactionCommand;
import org.home.blackjack.wallet.domain.wallet.CashAmount;
import org.home.blackjack.wallet.domain.wallet.Currency;
import org.home.blackjack.wallet.domain.wallet.Wallet;
import org.home.blackjack.wallet.domain.wallet.WalletId;
import org.home.blackjack.wallet.domain.wallet.WalletRepository;
import org.springframework.stereotype.Component;

/**
 * Driven port. App service for player action use-case.
 * 
 * @author Mate
 * 
 */
@Component
public class TransactionApplicationServiceImpl implements TransactionApplicationService {

	@Resource
	private WalletRepository walletRepository;
	
	/**
	 * Don't bother with checks and WalletHistory yet
	 */
	@WithPessimisticLock(repository = WalletRepository.class)
	@Override
	public TransactionResult handleTransaction(WalletId walletId, TransactionCommand transactionCommand) {

		Wallet wallet = walletRepository.find(walletId);
		
		//for sake of simplicity I don't create a new endpoint to create wallet
		if (wallet == null) {
			wallet = Wallet.emptyWallet(walletId, transactionCommand.amount().currency());
		}
		
		CashAmount originalAmount = wallet.amount();
		if (transactionCommand.isDebit()) {
			wallet.debit(transactionCommand.amount());
		} else {
			wallet.credit(transactionCommand.amount());
		} 
		
		walletRepository.update(wallet);
		
		return new TransactionResult(transactionCommand.id(), originalAmount,  wallet.amount());
	}

	@Override
	public CashAmount getBalance(WalletId walletId) {
		Wallet wallet = walletRepository.find(walletId);
		
		return wallet != null ? wallet.amount() : CashAmount.zero(Currency.CHIPS);
	}
	
	


}
