package org.home.blackjack.core.app.service.registration;

import org.home.blackjack.core.app.dto.Command;
import org.home.blackjack.core.domain.player.PlayerName;

public class RegistrationCommand implements Command {

	private final String name;

	public RegistrationCommand(String name) {
		this.name = name;
	}
	
	public PlayerName getName() {
		return new PlayerName(name);
	}
}
