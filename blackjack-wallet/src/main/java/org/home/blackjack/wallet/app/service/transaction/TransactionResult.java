package org.home.blackjack.wallet.app.service.transaction;

import org.home.blackjack.util.ddd.pattern.ValueObject;
import org.home.blackjack.wallet.domain.transaction.TransactionId;
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

	public TransactionId id() {
		return id;
	}

	public CashAmount originalAmount() {
		return originalAmount;
	}

	public CashAmount updatedAmount() {
		return updatedAmount;
	}

	
}
