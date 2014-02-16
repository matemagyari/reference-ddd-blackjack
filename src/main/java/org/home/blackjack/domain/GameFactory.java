package org.home.blackjack.domain;

import javax.inject.Inject;

public class GameFactory {
	
	@Inject
	private GameRepository gameRepository;
	@Inject
	private DeckFactory deckFactory;
	@Inject
	private EventDispatcher eventDispatcher;
	
	public Game createNewGame(PlayerId dealer, PlayerId player) {
		return new Game(dealer, player, deckFactory, eventDispatcher);
	}

}
