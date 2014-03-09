package org.home.blackjack.wallet.app.service.transaction;

import javax.annotation.Resource;
import javax.inject.Named;

import org.home.blackjack.util.locking.aspect.WithPessimisticLock;
import org.home.blackjack.wallet.domain.transaction.TransactionCommand;
import org.home.blackjack.wallet.domain.wallet.CashAmount;
import org.home.blackjack.wallet.domain.wallet.Wallet;
import org.home.blackjack.wallet.domain.wallet.WalletId;
import org.home.blackjack.wallet.domain.wallet.WalletRepository;

/**
 * Driven port. App service for player action use-case.
 * 
 * @author Mate
 * 
 */
@Named
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
		
		CashAmount originalAmount = wallet.amount();
		if (transactionCommand.isDebit()) {
			wallet.debit(transactionCommand.amount());
		} else {
			wallet.credit(transactionCommand.amount());
		} 
		
		walletRepository.update(wallet);
		
		return new TransactionResult(transactionCommand.id(), originalAmount,  wallet.amount());
	}


}
