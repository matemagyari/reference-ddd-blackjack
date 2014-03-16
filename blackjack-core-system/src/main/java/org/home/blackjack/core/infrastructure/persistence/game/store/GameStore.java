package org.home.blackjack.core.infrastructure.persistence.game.store;

import java.util.concurrent.locks.Lock;

import org.home.blackjack.core.domain.game.Game;
import org.home.blackjack.core.domain.game.core.GameID;
import org.home.blackjack.core.domain.shared.TableID;
import org.home.blackjack.core.infrastructure.persistence.shared.PersistenceObject;
import org.home.blackjack.core.infrastructure.persistence.shared.PersistenceObjectId;
import org.home.blackjack.core.infrastructure.persistence.shared.PersistenceStore;

public interface GameStore extends  PersistenceStore<Game, PersistenceObject<Game>, PersistenceObjectId<GameID>> {

	Lock getLockForKey(GameID key);

	PersistenceObject<Game> find(TableID tableId);
}
