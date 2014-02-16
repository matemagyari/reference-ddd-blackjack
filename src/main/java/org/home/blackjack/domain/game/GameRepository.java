package org.home.blackjack.domain.game;

import org.home.blackjack.domain.core.GameId;
import org.home.blackjack.util.hexagonal.DrivingPort;

public interface GameRepository extends DrivingPort {

	Game find(GameId gameId);

	void update(Game game);

}
