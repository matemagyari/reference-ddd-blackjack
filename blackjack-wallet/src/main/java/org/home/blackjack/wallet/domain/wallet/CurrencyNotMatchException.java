package org.home.blackjack.wallet.domain.wallet;

import org.home.blackjack.util.ddd.util.DomainException;

@SuppressWarnings("serial")
public class CurrencyNotMatchException extends DomainException {

	public CurrencyNotMatchException(Currency currency1, Currency currency2) {
		super("Currencies don't match " + currency1 + " vs " + currency2);
	}

}
