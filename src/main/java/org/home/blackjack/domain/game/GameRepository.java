package org.home.blackjack.domain.game;

import org.home.blackjack.domain.game.core.GameID;
import org.home.blackjack.util.ddd.pattern.Repository;

public interface GameRepository extends Repository<GameID, Game> {

	// will contain Game specific repository logic, if any
}
