package org.home.blackjack.app.eventhandler;

import javax.inject.Inject;
import javax.inject.Named;

import org.home.blackjack.app.event.ExternalEventPublisher;
import org.home.blackjack.domain.game.event.GameEvent;
import org.home.blackjack.util.ddd.pattern.events.DomainEvent;
import org.home.blackjack.util.ddd.pattern.events.EventSubscriber;

@Named
public class GameEventHandler implements EventSubscriber<GameEvent> {
    
    @Inject
    private ExternalEventPublisher externalEventPublisher;

    @Override
    public boolean subscribedTo(DomainEvent event) {
        return event instanceof GameEvent;
    }

    @Override
    public void handleEvent(GameEvent event) {
        externalEventPublisher.publish(event);
    }

}
