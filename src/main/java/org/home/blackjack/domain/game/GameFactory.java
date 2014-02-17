package org.home.blackjack.domain.game;

import javax.inject.Inject;

import org.home.blackjack.domain.common.EventPublisher;
import org.home.blackjack.domain.shared.PlayerId;

public class GameFactory {
	
	@Inject
	private DeckFactory deckFactory;
	@Inject
	private EventPublisher eventPublisher;
	
	public Game createNewGame(PlayerId dealer, PlayerId player) {
		return new GameImpl(dealer, player, deckFactory, eventPublisher);
	}

}
