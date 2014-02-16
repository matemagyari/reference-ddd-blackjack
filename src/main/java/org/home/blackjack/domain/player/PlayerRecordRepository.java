package org.home.blackjack.domain.player;

import org.home.blackjack.domain.core.PlayerId;

public interface PlayerRecordRepository {

	PlayerRecord find(PlayerId winner);

	void update(PlayerRecord playerRecord);

}
