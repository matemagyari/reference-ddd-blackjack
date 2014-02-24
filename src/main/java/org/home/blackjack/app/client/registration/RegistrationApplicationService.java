package org.home.blackjack.app.client.registration;

import javax.inject.Inject;

import org.home.blackjack.domain.player.Player;
import org.home.blackjack.domain.player.PlayerName;
import org.home.blackjack.domain.player.PlayerRepository;
import org.home.blackjack.domain.shared.PlayerID;
import org.home.blackjack.util.marker.hexagonal.DrivenPort;

public final class RegistrationApplicationService implements DrivenPort {

	@Inject
	private PlayerRepository playerRepository;

	public void playerJoins(final PlayerName playerName) {

		playerRepository.create(new Player(new PlayerID(), playerName));
	}
}
