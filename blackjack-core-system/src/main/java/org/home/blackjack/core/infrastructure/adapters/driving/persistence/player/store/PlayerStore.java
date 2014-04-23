package org.home.blackjack.core.infrastructure.adapters.driving.persistence.player.store;

import java.util.List;
import java.util.concurrent.locks.Lock;

import org.home.blackjack.core.domain.player.Player;
import org.home.blackjack.core.domain.shared.PlayerID;
import org.home.blackjack.util.ddd.pattern.infrastructure.persistence.PersistenceObject;
import org.home.blackjack.util.ddd.pattern.infrastructure.persistence.PersistenceObjectId;
import org.home.blackjack.util.ddd.pattern.infrastructure.persistence.PersistenceStore;

public interface PlayerStore extends PersistenceStore<Player, PersistenceObject<Player>, PersistenceObjectId<PlayerID>> {
    
    Lock getLockForKey(PlayerID key);

    List<PersistenceObject<Player>> findAllSortedByWinNumber();

}
