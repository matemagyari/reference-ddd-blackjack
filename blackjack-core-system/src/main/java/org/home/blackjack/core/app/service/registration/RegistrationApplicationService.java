package org.home.blackjack.core.app.service.registration;

import javax.annotation.Resource;
import javax.inject.Named;

import org.home.blackjack.core.domain.RegisterService;
import org.home.blackjack.core.domain.player.PlayerName;
import org.home.blackjack.core.domain.shared.PlayerID;
import org.home.blackjack.util.marker.hexagonal.DrivenPort;

@Named
public final class RegistrationApplicationService implements DrivenPort {

	@Resource
	private RegisterService registerService;

	public PlayerID playerJoins(PlayerName playerName) {
		return registerService.registerPlayer(playerName);
	}
}
