package org.home.blackjack.app.registration.client;

import org.home.blackjack.domain.player.PlayerRecord;
import org.home.blackjack.domain.player.PlayerRecordRepository;
import org.home.blackjack.domain.shared.PlayerId;
import org.home.blackjack.util.marker.hexagonal.DrivenPort;

public class RegistrationApplicationService implements DrivenPort {
	
	private PlayerRecordRepository playerRecordRepository;
	
	public void playerJoins(PlayerId playerId) {
		playerRecordRepository.create(new PlayerRecord(playerId));
	}

}
