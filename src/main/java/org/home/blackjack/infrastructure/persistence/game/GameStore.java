package org.home.blackjack.infrastructure.persistence.game;

import org.home.blackjack.domain.game.Game;
import org.home.blackjack.domain.game.core.GameID;
import org.home.blackjack.infrastructure.persistence.shared.PersistenceObject;
import org.home.blackjack.infrastructure.persistence.shared.PersistenceObjectId;
import org.home.blackjack.infrastructure.persistence.shared.PersistenceStore;

public interface GameStore extends  PersistenceStore<Game, PersistenceObject<Game>, PersistenceObjectId<GameID>> {
}
