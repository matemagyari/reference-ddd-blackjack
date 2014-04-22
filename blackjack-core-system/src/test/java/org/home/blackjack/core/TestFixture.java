package org.home.blackjack.core;

import java.util.concurrent.atomic.AtomicLong;

import org.home.blackjack.core.domain.game.DeckFactory;
import org.home.blackjack.core.domain.game.Game;
import org.home.blackjack.core.domain.game.core.GameID;
import org.home.blackjack.core.domain.shared.PlayerID;
import org.home.blackjack.core.domain.shared.TableID;
import org.home.blackjack.util.ddd.pattern.domain.events.DomainEventPublisher;
import org.home.blackjack.util.ddd.pattern.domain.model.DomainEvent;

public class TestFixture {
	
	private static AtomicLong idGenerator = new AtomicLong();

	public static PlayerID aPlayerId() {
		return PlayerID.createFrom(idGenerator.incrementAndGet() + "");
	}
	public static Game aGame() {
	    return new Game(aGameId(), aTableId(), aPlayerId(), aPlayerId(), new DeckFactory(), aDomainEventPublisher());
	}

    private static TableID aTableId() {
        return TableID.createFrom(idGenerator.incrementAndGet() + "");
    }
    private static GameID aGameId() {
        return GameID.createFrom(idGenerator.incrementAndGet() + "");
    }
    private static DomainEventPublisher aDomainEventPublisher() {
        return new DomainEventPublisher() {
            @Override
            public <T extends DomainEvent> void publish(T event) {
            }
        };
    }

}
