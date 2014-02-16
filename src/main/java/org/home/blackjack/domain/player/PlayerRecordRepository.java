package org.home.blackjack.domain.player;

import org.home.blackjack.domain.core.PlayerId;
import org.home.blackjack.util.hexagonal.DrivingPort;

public interface PlayerRecordRepository extends DrivingPort {

	PlayerRecord find(PlayerId winner);

	void update(PlayerRecord playerRecord);

	void create(PlayerRecord playerRecord);

}
