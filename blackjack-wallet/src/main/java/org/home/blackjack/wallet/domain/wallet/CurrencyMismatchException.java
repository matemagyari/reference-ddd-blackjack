package org.home.blackjack.wallet.domain.wallet;

import org.home.blackjack.util.ddd.util.DomainException;

@SuppressWarnings("serial")
public class CurrencyMismatchException extends DomainException {

	public CurrencyMismatchException(Currency currency1, Currency currency2) {
		super("Currencies don't match " + currency1 + " vs " + currency2);
	}

}
