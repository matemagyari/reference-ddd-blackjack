package org.home.blackjack.domain.player;

import org.home.blackjack.util.ddd.pattern.Repository;

public interface PlayerRepository extends Repository<PlayerID, Player> {

	// will contain Player specific repository methods, if any
}
