package org.home.blackjack.infrastructure.persistence.player.store;

import org.home.blackjack.domain.player.Player;
import org.home.blackjack.domain.shared.PlayerID;
import org.home.blackjack.infrastructure.persistence.shared.PersistenceObject;
import org.home.blackjack.infrastructure.persistence.shared.PersistenceObjectId;
import org.home.blackjack.infrastructure.persistence.shared.PersistenceStore;

public interface PlayerStore extends PersistenceStore<Player, PersistenceObject<Player>, PersistenceObjectId<PlayerID>> {

}
