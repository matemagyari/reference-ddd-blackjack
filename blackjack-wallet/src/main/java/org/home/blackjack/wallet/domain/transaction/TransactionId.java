package org.home.blackjack.wallet.domain.transaction;

import org.home.blackjack.util.ddd.pattern.ID;

public class TransactionId extends ID {

	private TransactionId(String strId) {
		super(strId);
	}

	public static TransactionId createFrom(String strId) {
		return new TransactionId(strId);
	}

}
