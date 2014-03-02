package org.home.blackjack.util.ddd.util;


@SuppressWarnings("serial")
public class ValidationException extends DomainException {

	public ValidationException(String msg) {
		super(msg);
	}

	public ValidationException() {
		super("validation exception");
	}

}
