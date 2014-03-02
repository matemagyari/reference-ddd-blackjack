package org.home.blackjack.core;

import java.lang.reflect.Field;

public class ReflectionHelper {

	public static final void setField(final String fieldName, final Object value, final Object target)
			throws NoSuchFieldException, IllegalAccessException {

		Field field = target.getClass().getField(fieldName);
		field.setAccessible(true);
		field.set(target, value);
		field.setAccessible(false);
	}

	public static final void setField(final String fieldName, final Object value, final Class<?> clazz)
			throws NoSuchFieldException, IllegalAccessException {

		Field field = clazz.getDeclaredField(fieldName);
		field.setAccessible(true);
		field.set(clazz, value);
		field.setAccessible(false);
	}
}
