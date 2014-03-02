package org.home.blackjack.wallet.app.client.transaction;

import org.home.blackjack.util.locking.aspect.LockVal;
import org.home.blackjack.util.marker.hexagonal.DrivenPort;
import org.home.blackjack.wallet.domain.transaction.TransactionCommand;
import org.home.blackjack.wallet.domain.wallet.CashAmount;
import org.home.blackjack.wallet.domain.wallet.WalletId;


public interface TransactionApplicationService extends DrivenPort {

	TransactionResult handleTransaction(@LockVal WalletId walletId, TransactionCommand transactionCommand);
	
	CashAmount getBalance(WalletId walletId);

}