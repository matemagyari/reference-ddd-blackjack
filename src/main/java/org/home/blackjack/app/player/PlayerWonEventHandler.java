package org.home.blackjack.app.player;

import javax.inject.Inject;
import javax.inject.Named;

import org.home.blackjack.app.event.ExternalEventPublisher;
import org.home.blackjack.domain.common.events.EventSubscriber;
import org.home.blackjack.domain.player.event.PlayerWonEvent;
import org.home.blackjack.util.ddd.pattern.DomainEvent;

@Named
public class PlayerWonEventHandler implements EventSubscriber<PlayerWonEvent> {
    
    @Inject
    private ExternalEventPublisher externalEventPublisher;

    @Override
    public boolean subscribedTo(DomainEvent event) {
        return event instanceof PlayerWonEvent;
    }

    @Override
    public void handleEvent(PlayerWonEvent event) {
        externalEventPublisher.publish(event);
    }

}
