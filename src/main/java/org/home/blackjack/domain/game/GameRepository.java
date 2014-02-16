package org.home.blackjack.domain.game;

import org.home.blackjack.domain.core.GameId;

public interface GameRepository {

	Game find(GameId gameId);

	void update(Game game);

}
