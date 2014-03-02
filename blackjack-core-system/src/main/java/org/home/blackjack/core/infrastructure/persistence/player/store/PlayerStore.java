package org.home.blackjack.core.infrastructure.persistence.player.store;

import org.home.blackjack.core.domain.player.Player;
import org.home.blackjack.core.domain.shared.PlayerID;
import org.home.blackjack.core.infrastructure.persistence.shared.PersistenceObject;
import org.home.blackjack.core.infrastructure.persistence.shared.PersistenceObjectId;
import org.home.blackjack.core.infrastructure.persistence.shared.PersistenceStore;

public interface PlayerStore extends PersistenceStore<Player, PersistenceObject<Player>, PersistenceObjectId<PlayerID>> {

}
