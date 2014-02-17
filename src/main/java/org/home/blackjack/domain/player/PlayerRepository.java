package org.home.blackjack.domain.player;

import org.home.blackjack.domain.shared.PlayerId;
import org.home.blackjack.util.marker.hexagonal.DrivingPort;

public interface PlayerRepository extends DrivingPort {

	Player find(PlayerId winner);

	void update(Player player);

	void create(Player player);

}
