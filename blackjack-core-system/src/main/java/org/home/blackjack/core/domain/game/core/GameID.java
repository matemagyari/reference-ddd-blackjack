package org.home.blackjack.core.domain.game.core;

import org.home.blackjack.util.ddd.pattern.domain.model.ID;

public class GameID extends ID {

    private GameID(String id) {
		super(id);
	}

	public static GameID createFrom(String id) {
		return new GameID(id);
	}
}
