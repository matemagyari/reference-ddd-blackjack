package org.home.blackjack.wallet.domain.wallet;


import org.home.blackjack.util.ddd.pattern.domain.model.AggregateRoot;
import org.home.blackjack.util.ddd.pattern.domain.validation.Validator;

public class Wallet extends AggregateRoot<WalletId> {
	
	private CashAmount amount;
	
	public static Wallet emptyWallet(WalletId walletId, Currency currency) {
		return new Wallet(walletId, CashAmount.zero(currency));
	}

	public Wallet(WalletId id, CashAmount amount) {
		super(id);
		Validator.notNull(amount);
		this.amount = amount;
	}
	
	//I choose to put the exception logic here, since CashAmount could be a negative value
	public void debit(CashAmount debit) {
		if (amount.isLessThan(debit)) {
			throw new CannotAffordException(amount, debit);
		}
		amount = amount.subtract(debit);
	}
	public void credit(CashAmount credit) {
		amount = amount.add(credit);
	}
	
	public CashAmount amount() {
		return amount;
	}

}
