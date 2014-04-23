package org.home.blackjack.core.infrastructure.adapters.driving.persistence.game.store;

import java.util.concurrent.locks.Lock;

import org.home.blackjack.core.domain.game.Game;
import org.home.blackjack.core.domain.game.core.GameID;
import org.home.blackjack.core.domain.shared.TableID;
import org.home.blackjack.util.ddd.pattern.infrastructure.persistence.PersistenceObject;
import org.home.blackjack.util.ddd.pattern.infrastructure.persistence.PersistenceObjectId;
import org.home.blackjack.util.ddd.pattern.infrastructure.persistence.PersistenceStore;

public interface GameStore extends PersistenceStore<Game, PersistenceObject<Game>, PersistenceObjectId<GameID>> {

	Lock getLockForKey(GameID key);

	PersistenceObject<Game> find(TableID tableId);
}
