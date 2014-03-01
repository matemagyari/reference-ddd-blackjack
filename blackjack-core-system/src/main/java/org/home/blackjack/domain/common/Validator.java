package org.home.blackjack.domain.common;

public class Validator {
	
	public static void notNull(Object obj) {
		if (obj == null) {
			throw new ValidationException();
		}
	}
	public static void notNull(Object... objs) {
		for (Object object : objs) {
			notNull(object);
		}
	}
}
