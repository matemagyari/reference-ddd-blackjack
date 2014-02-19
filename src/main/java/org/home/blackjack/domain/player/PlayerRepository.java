package org.home.blackjack.domain.player;

import org.home.blackjack.domain.shared.PlayerID;
import org.home.blackjack.util.ddd.pattern.Repository;

public interface PlayerRepository extends Repository<PlayerID, Player> {

	void update(Player player);

	Player find(PlayerID winner);

	void create(Player player);

}
