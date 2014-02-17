package org.home.blackjack.infrastructure.persistence.game;

import javax.inject.Named;

import org.home.blackjack.domain.game.Game;
import org.home.blackjack.domain.game.core.GameID;
import org.home.blackjack.infrastructure.persistence.InMemoryGenericRepository;

@Named
public final class InMemoryGameRepository extends InMemoryGenericRepository<GameID, Game> {

	// will contain Game specific logic implementation, if any
}
