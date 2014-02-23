package org.home.blackjack.domain.table.event;

import org.home.blackjack.domain.game.Game;
import org.home.blackjack.domain.table.core.TableID;

public class TableIsFullEvent extends TableEvent {

	private Game game;

	public TableIsFullEvent(TableID id, Game game) {
		super(id);
		this.game = game;
	}

	public Game getGame() {
		return game;
	}

}
