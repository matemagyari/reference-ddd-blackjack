package org.home.blackjack.core.domain.game;

import org.home.blackjack.core.domain.game.core.GameID;
import org.home.blackjack.core.domain.shared.TableID;
import org.home.blackjack.util.ddd.pattern.domain.Repository;

public interface GameRepository extends Repository<GameID, Game> {

	Game find(GameID gameID);
	Game find(TableID tableId);
	void update(Game game);
	void create(Game game);
}
