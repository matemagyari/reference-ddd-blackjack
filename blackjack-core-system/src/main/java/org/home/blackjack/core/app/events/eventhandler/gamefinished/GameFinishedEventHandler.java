package org.home.blackjack.core.app.events.eventhandler.gamefinished;

import org.home.blackjack.core.domain.game.event.GameFinishedEvent;
import org.home.blackjack.util.ddd.pattern.app.event.DomainEventSubscriber;
import org.home.blackjack.util.ddd.pattern.domain.events.DomainEvent;

public abstract class GameFinishedEventHandler implements DomainEventSubscriber<GameFinishedEvent> {
    
    @Override
    public boolean subscribedTo(DomainEvent event) {
        return event instanceof GameFinishedEvent;
    }

}
