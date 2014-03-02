package org.home.blackjack.wallet.domain.transaction;

import org.home.blackjack.util.ddd.pattern.ValueObject;
import org.home.blackjack.wallet.domain.wallet.CashAmount;

public class TransactionResult extends ValueObject {

	private final TransactionId id;
	private final CashAmount originalAmount;
	private final CashAmount updatedAmount;

	public TransactionResult(TransactionId id, CashAmount originalAmount, CashAmount updatedAmount) {
		this.id = id;
		this.originalAmount = originalAmount;
		this.updatedAmount = updatedAmount;
	}

}
