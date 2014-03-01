package org.home.blackjack.domain.game;

import org.home.blackjack.domain.game.core.GameID;
import org.home.blackjack.domain.shared.PlayerID;
import org.home.blackjack.domain.table.core.TableID;
import org.home.blackjack.util.ddd.pattern.events.DomainEvent;
import org.home.blackjack.util.ddd.pattern.events.DomainEventPublisher;


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
