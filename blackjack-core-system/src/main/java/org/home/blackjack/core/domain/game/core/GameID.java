package org.home.blackjack.core.domain.game.core;

import org.home.blackjack.util.ddd.pattern.ID;

public class GameID extends ID {
	public GameID() {
		super();
	}
	private GameID(String id) {
		super(id);
	}

	public static GameID createFrom(String id) {
		return new GameID(id);
	}
}
