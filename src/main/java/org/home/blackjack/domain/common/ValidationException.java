package org.home.blackjack.domain.common;

@SuppressWarnings("serial")
public class ValidationException extends DomainException {

	public ValidationException(String msg) {
		super(msg);
	}

	public ValidationException() {
		super("validation exception");
	}

}
