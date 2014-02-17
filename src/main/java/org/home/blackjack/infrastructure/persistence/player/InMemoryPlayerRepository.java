package org.home.blackjack.infrastructure.persistence.player;

import javax.inject.Named;

import org.home.blackjack.domain.player.Player;
import org.home.blackjack.domain.shared.PlayerID;
import org.home.blackjack.infrastructure.persistence.InMemoryGenericRepository;

@Named
public final class InMemoryPlayerRepository extends InMemoryGenericRepository<PlayerID, Player> {

	// will contain Game specific logic implementation, if any
}
