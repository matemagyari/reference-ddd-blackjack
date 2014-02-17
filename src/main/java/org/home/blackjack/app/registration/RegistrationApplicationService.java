package org.home.blackjack.app.registration;

import javax.inject.Inject;

import org.home.blackjack.domain.player.Player;
import org.home.blackjack.domain.player.PlayerID;
import org.home.blackjack.domain.player.PlayerRepository;
import org.home.blackjack.util.marker.hexagonal.DrivenPort;

public class RegistrationApplicationService implements DrivenPort {

	@Inject
	private PlayerRepository playerRepository;

	public void playerJoins(PlayerID playerID) {

		playerRepository.put(new Player(playerID));
	}
}
