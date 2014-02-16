package org.home.blackjack.domain.game;

import javax.inject.Inject;

import org.home.blackjack.domain.common.EventDispatcher;
import org.home.blackjack.domain.shared.PlayerId;

public class GameFactory {
	
	@Inject
	private DeckFactory deckFactory;
	@Inject
	private EventDispatcher eventDispatcher;
	
	public Game createNewGame(PlayerId dealer, PlayerId player) {
		return new GameImpl(dealer, player, deckFactory, eventDispatcher);
	}

}
