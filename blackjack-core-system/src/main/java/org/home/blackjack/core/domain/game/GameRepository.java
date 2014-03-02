package org.home.blackjack.core.domain.game;

import org.home.blackjack.core.domain.game.core.GameID;
import org.home.blackjack.util.ddd.pattern.Repository;

public interface GameRepository extends Repository<GameID, Game> {

	Game find(GameID gameID);
	void update(Game game);
	void create(Game game);
}
