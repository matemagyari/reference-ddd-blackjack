package org.home.blackjack.domain.game;

import javax.inject.Inject;

import org.home.blackjack.domain.core.PlayerId;
import org.home.blackjack.domain.coreservice.EventDispatcher;

public class GameFactory {
	
	@Inject
	private DeckFactory deckFactory;
	@Inject
	private EventDispatcher eventDispatcher;
	
	public Game createNewGame(PlayerId dealer, PlayerId player) {
		return new GameImpl(dealer, player, deckFactory, eventDispatcher);
	}

}
