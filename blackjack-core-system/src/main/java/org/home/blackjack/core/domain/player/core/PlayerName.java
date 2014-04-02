package org.home.blackjack.core.domain.player.core;

import org.apache.commons.lang3.Validate;
import org.home.blackjack.util.ddd.pattern.ValueObject;

public final class PlayerName extends ValueObject {

	private final String text;

	public PlayerName(final String text) {
		Validate.notBlank(text);
		this.text = text;
	}

	public String getText() {
		return text;
	}

	@Override
	public String toString() {
		return text;
	}
}
