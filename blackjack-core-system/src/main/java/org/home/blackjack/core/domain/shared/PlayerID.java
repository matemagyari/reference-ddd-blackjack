package org.home.blackjack.core.domain.shared;

import org.home.blackjack.util.ddd.pattern.domain.ID;

/**
 * Value Object
 * 
 * @author Mate
 * 
 */
public class PlayerID extends ID {

	private PlayerID(String id) {
		super(id);
	}

	public static PlayerID createFrom(String id) {
		return new PlayerID(id);
	}
}
