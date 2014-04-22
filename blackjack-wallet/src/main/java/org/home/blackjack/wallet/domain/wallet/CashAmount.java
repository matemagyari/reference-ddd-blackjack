package org.home.blackjack.wallet.domain.wallet;

import java.math.BigDecimal;

import org.home.blackjack.util.ddd.pattern.domain.model.ValueObject;
import org.home.blackjack.util.ddd.pattern.domain.validation.Validator;

public class CashAmount extends ValueObject {
	
	private final Currency currency;
	private final BigDecimal amount;

	public CashAmount(BigDecimal amount, Currency currency) {
		Validator.notNull(amount, currency);
		this.amount = amount;
		this.currency = currency;
	}
	
	public static CashAmount createFrom(String string) {
		String[] split = string.split(" ");
		BigDecimal amount = new BigDecimal(split[0]);
		Currency currency = Currency.valueOf(split[1]);
		return new CashAmount(amount, currency);
	}

	public static CashAmount createFrom(String amount, Currency currency) {
		return new CashAmount(new BigDecimal(amount), currency);
	}

	
	public boolean isLessThan(CashAmount that) {
		checkCurrency(that);
		return amount().compareTo(that.amount()) == -1;
	}
	
	public CashAmount subtract(CashAmount that) {
		checkCurrency(that);
		return new CashAmount(amount.subtract(that.amount()), currency);
	}
	

	public CashAmount add(CashAmount that) {
		checkCurrency(that);
		return new CashAmount(amount.add(that.amount()), currency);
	}
	
	public static CashAmount zero(Currency currency) {
		return new CashAmount(BigDecimal.ZERO, currency);
	}

	private void checkCurrency(CashAmount that) {
		if (!this.currency().equals(that.currency())) {
			throw new CurrencyMismatchException(currency(), that.currency());
		}
	}

	private BigDecimal amount() {
		return amount;
	}
	
	public Currency currency() {
		return currency;
	}


	@Override
	public String toString() {
		return "[currency=" + currency + ", amount=" + amount + "]";
	}

}
