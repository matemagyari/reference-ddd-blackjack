package org.home.blackjack.app.registration.client;

import org.home.blackjack.domain.player.Player;
import org.home.blackjack.domain.player.PlayerName;
import org.home.blackjack.domain.player.PlayerRepository;
import org.home.blackjack.domain.shared.PlayerId;
import org.home.blackjack.util.marker.hexagonal.DrivenPort;

public class RegistrationApplicationService implements DrivenPort {
	
	private PlayerRepository playerRepository;
	
	public void playerJoins(PlayerId playerId) {
	    //TODO - playername must be unique
		playerRepository.create(new Player(playerId, new PlayerName("johnny")));
	}

}
