package org.home.blackjack.domain.common;

import org.home.blackjack.domain.game.core.GameId;

public interface EventStore {

    /**
     * Flush only events for this Game
     */
	void flush(GameId gameId);

}
