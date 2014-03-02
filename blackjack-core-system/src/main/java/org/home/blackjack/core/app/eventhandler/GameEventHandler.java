package org.home.blackjack.core.app.eventhandler;

import javax.annotation.Resource;
import javax.inject.Named;

import org.home.blackjack.core.app.event.ExternalEventPublisher;
import org.home.blackjack.core.domain.game.event.GameEvent;
import org.home.blackjack.util.ddd.pattern.events.DomainEvent;
import org.home.blackjack.util.ddd.pattern.events.DomainEventSubscriber;

@Named
//public class GameEventHandler extends BufferingAsyncDomainEventSubscriber<GameEvent> {
public class GameEventHandler implements DomainEventSubscriber<GameEvent> {
   
    @Resource
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
