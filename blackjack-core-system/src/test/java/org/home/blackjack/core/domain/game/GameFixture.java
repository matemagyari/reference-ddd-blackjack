package org.home.blackjack.core.domain.game;

import java.util.UUID;

import org.home.blackjack.core.domain.game.core.GameID;
import org.home.blackjack.core.domain.shared.PlayerID;
import org.home.blackjack.core.domain.shared.TableID;
import org.home.blackjack.util.ddd.pattern.domain.model.DomainEvent;
import org.home.blackjack.util.ddd.pattern.domain.model.DomainEventPublisher;


public class GameFixture {
	
	public static Game aGame() {
		return new Game(aGameID(), aTableID(), aPlayerID(), aPlayerID(), new DeckFactory(), eventPublisherDummy());
	}
	
	public static TableID aTableID() {
		return TableID.createFrom(UUID.randomUUID().toString());
	}
	public static PlayerID aPlayerID() {
		return  PlayerID.createFrom(UUID.randomUUID().toString());
	}

	public static GameID aGameID() {
		return GameID.createFrom(UUID.randomUUID().toString());
	}

	private static DomainEventPublisher eventPublisherDummy() {
		return new DomainEventPublisher() {
			@Override
			public void publish(DomainEvent event) {
			}
		};
	}

}
