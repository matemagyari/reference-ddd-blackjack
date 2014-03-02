package org.home.blackjack.core.app.bootstrap;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.home.blackjack.core.domain.table.LobbyManager;

public class Bootstrapper {
	
	@Inject
	private LobbyManager lobbyManager;
	
	@PostConstruct
	private void setupLobby() {
		lobbyManager.setupLobbyBeforePlayersCome();
	}
}
