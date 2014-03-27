package org.home.blackjack.core.app.service.registration;

import org.home.blackjack.core.app.dto.Command;
import org.home.blackjack.core.domain.player.PlayerName;

public class RegistrationCommand implements Command {

	private final PlayerName name;

	public RegistrationCommand(PlayerName name) {
		this.name = name;
	}
	
	public PlayerName getName() {
		return name;
	}
}
