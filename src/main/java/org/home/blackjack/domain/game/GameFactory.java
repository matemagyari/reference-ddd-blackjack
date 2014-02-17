package org.home.blackjack.domain.game;

import javax.inject.Inject;

import org.home.blackjack.domain.player.PlayerID;

public class GameFactory {

	@Inject
	private DeckFactory deckFactory;

	public Game createNewGame(PlayerID dealer, PlayerID player) {

		return new Game(new GameID(), dealer, player, deckFactory);
	}

}
