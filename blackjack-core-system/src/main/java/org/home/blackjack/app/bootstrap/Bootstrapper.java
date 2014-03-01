package org.home.blackjack.app.bootstrap;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.home.blackjack.domain.table.LobbyManager;

public class Bootstrapper {
	
	@Inject
	private LobbyManager lobbyManager;
	
	@PostConstruct
	private void setupLobby() {
		lobbyManager.setupLobbyBeforePlayersCome();
	}
}
