package org.home.blackjack.domain.player;

import org.apache.commons.lang3.Validate;
import org.home.blackjack.util.ddd.pattern.ValueObject;

/**
 * Value Object
 * 
 * @author mate.magyari
 * 
 */
public final class PlayerName extends ValueObject {

	private String text;

	public PlayerName(final String text) {

		setText(text);
	}

	public String getText() {

		return text;
	}

	private void setText(final String text) {

		Validate.notBlank(text);
		this.text = text;
	}
}
