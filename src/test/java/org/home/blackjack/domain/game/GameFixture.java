package org.home.blackjack.domain.game;

import org.home.blackjack.domain.game.core.GameID;
import org.home.blackjack.domain.shared.PlayerID;
import org.home.blackjack.util.ddd.pattern.DomainEvent;
import org.home.blackjack.util.ddd.pattern.EventPublisher;


public class GameFixture {
	
	public static Game aGame() {
		return new Game(new GameID(), new PlayerID(), new PlayerID(), new DeckFactory(), eventPublisherDummy());
	}

	private static EventPublisher eventPublisherDummy() {
		return new EventPublisher() {
			@Override
			public void publish(DomainEvent event) {
			}
		};
	}


}
