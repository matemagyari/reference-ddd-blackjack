package org.home.blackjack.util.ddd.util;


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
