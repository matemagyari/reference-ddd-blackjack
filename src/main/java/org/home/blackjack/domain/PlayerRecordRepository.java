package org.home.blackjack.domain;

public interface PlayerRecordRepository {

	PlayerRecord find(PlayerId winner);

	void update(PlayerRecord playerRecord);

}
