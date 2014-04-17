package org.home.blackjack.core.domain.game;

import org.home.blackjack.core.domain.game.DeckFactory;
import org.home.blackjack.core.domain.game.Game;
import org.home.blackjack.core.domain.game.core.GameID;
import org.home.blackjack.core.domain.shared.PlayerID;
import org.home.blackjack.core.domain.shared.TableID;
import org.home.blackjack.util.ddd.pattern.domain.events.DomainEvent;
import org.home.blackjack.util.ddd.pattern.domain.events.DomainEventPublisher;


public class GameFixture {
	
	public static Game aGame() {
		return new Game(new GameID(), new TableID(), new PlayerID(), new PlayerID(), new DeckFactory(), eventPublisherDummy());
	}
	
	public static TableID aTableID() {
		return new TableID();
	}
	public static PlayerID aPlayerID() {
		return new PlayerID();
	}

	public static GameID aGameID() {
		return new GameID();
	}

	private static DomainEventPublisher eventPublisherDummy() {
		return new DomainEventPublisher() {
			@Override
			public void publish(DomainEvent event) {
			}
		};
	}

}
