package org.home.blackjack.app.registration.client;

import org.home.blackjack.domain.core.PlayerId;
import org.home.blackjack.domain.player.PlayerRecord;
import org.home.blackjack.domain.player.PlayerRecordRepository;
import org.home.blackjack.util.hexagonal.DrivenPort;

public class RegistrationHandler implements DrivenPort {
	
	private PlayerRecordRepository playerRecordRepository;
	
	public void playerJoins(PlayerId playerId) {
		playerRecordRepository.create(new PlayerRecord(playerId));
	}

}
