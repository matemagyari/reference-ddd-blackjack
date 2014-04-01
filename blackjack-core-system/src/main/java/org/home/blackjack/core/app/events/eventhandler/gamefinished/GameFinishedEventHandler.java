package org.home.blackjack.core.app.events.eventhandler.gamefinished;

import org.home.blackjack.core.domain.game.event.GameFinishedEvent;
import org.home.blackjack.util.ddd.pattern.events.DomainEvent;
import org.home.blackjack.util.ddd.pattern.events.DomainEventSubscriber;

public abstract class GameFinishedEventHandler implements DomainEventSubscriber<GameFinishedEvent> {
    
    @Override
    public boolean subscribedTo(DomainEvent event) {
        return event instanceof GameFinishedEvent;
    }

}
