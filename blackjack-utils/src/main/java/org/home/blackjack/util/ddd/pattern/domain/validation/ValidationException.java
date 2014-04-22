package org.home.blackjack.util.ddd.pattern.domain.validation;

import org.home.blackjack.util.ddd.pattern.domain.exception.DomainException;


@SuppressWarnings("serial")
public class ValidationException extends DomainException {

	public ValidationException(String msg) {
		super(msg);
	}

	public ValidationException() {
		super("validation exception");
	}

}
