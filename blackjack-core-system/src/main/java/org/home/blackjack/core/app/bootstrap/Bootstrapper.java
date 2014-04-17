package org.home.blackjack.core.app.bootstrap;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.home.blackjack.core.domain.table.LobbyManager;
import org.springframework.context.annotation.Lazy;

@Lazy(value=false)
@Named
public class Bootstrapper {
	
	@Inject
	private LobbyManager lobbyManager;
	
    @PostConstruct @SuppressWarnings("unused")
	private void setupLobby() {
		lobbyManager.setupLobbyBeforePlayersCome();
	}
}
