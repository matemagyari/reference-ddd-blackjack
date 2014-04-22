package org.home.blackjack.wallet.domain.wallet;

import org.home.blackjack.util.ddd.pattern.domain.exception.DomainException;

@SuppressWarnings("serial")
public class CannotAffordException extends DomainException {

	public CannotAffordException(CashAmount amount, CashAmount debit) {
		super("Can't debit this much " + debit + " from wallet of " + amount);
	}

}
