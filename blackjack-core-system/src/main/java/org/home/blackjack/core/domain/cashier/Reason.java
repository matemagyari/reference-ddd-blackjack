package org.home.blackjack.core.domain.cashier;

import org.home.blackjack.util.ddd.pattern.domain.ValueObject;

public class Reason extends ValueObject {

	private final String reasonMsg;

	public Reason(String reasonMsg) {
		this.reasonMsg = reasonMsg;
	}
	
	public String reasonMsg() {
		return reasonMsg;
	}

}
