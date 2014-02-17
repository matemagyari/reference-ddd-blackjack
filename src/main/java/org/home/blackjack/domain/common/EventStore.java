package org.home.blackjack.domain.common;

import org.home.blackjack.domain.game.GameID;

public interface EventStore {

	/**
	 * Flush only events for this Game
	 */
	void flush(GameID gameID);

}
