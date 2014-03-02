package org.home.blackjack.core.app.client.registration;

import javax.annotation.Resource;
import javax.inject.Named;

import org.home.blackjack.core.domain.player.Player;
import org.home.blackjack.core.domain.player.PlayerName;
import org.home.blackjack.core.domain.player.PlayerRepository;
import org.home.blackjack.core.domain.shared.PlayerID;
import org.home.blackjack.util.marker.hexagonal.DrivenPort;

@Named
public final class RegistrationApplicationService implements DrivenPort {

	@Resource
	private PlayerRepository playerRepository;

	public void playerJoins(final PlayerName playerName) {

		playerRepository.create(new Player(new PlayerID(), playerName));
	}
}
