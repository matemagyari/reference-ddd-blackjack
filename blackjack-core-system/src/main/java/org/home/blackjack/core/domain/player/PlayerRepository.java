package org.home.blackjack.core.domain.player;

import org.home.blackjack.core.domain.shared.PlayerID;
import org.home.blackjack.util.ddd.pattern.Repository;

public interface PlayerRepository extends Repository<PlayerID, Player> {

	void update(Player player);

	Player find(PlayerID winner);

	void create(Player player);

	void clear();

}
