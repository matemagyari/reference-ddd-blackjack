package org.home.blackjack.app.registration;

import javax.inject.Inject;

import org.home.blackjack.domain.player.Player;
import org.home.blackjack.domain.player.PlayerName;
import org.home.blackjack.domain.player.PlayerRepository;
import org.home.blackjack.domain.shared.PlayerID;
import org.home.blackjack.util.ddd.pattern.EventBus;
import org.home.blackjack.util.marker.hexagonal.DrivenPort;

public final class RegistrationApplicationService implements DrivenPort {

	@Inject
	private EventBus eventBus;

	@Inject
	private PlayerRepository playerRepository;

	public void playerJoins(final PlayerID playerID, final PlayerName playerName) {

		playerRepository.put(new Player(playerID, playerName, eventBus));
	}
}
