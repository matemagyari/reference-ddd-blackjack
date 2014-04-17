package org.home.blackjack.core.app.events.eventhandler;

import javax.annotation.Resource;
import javax.inject.Named;

import org.home.blackjack.core.app.events.external.ExternalDomainEvent;
import org.home.blackjack.core.app.events.external.ExternalEventPublisher;
import org.home.blackjack.core.domain.game.event.GameEvent;
import org.home.blackjack.core.domain.game.event.PlayerCardDealtEvent;
import org.home.blackjack.util.ddd.pattern.app.event.DomainEventSubscriber;
import org.home.blackjack.util.ddd.pattern.domain.events.DomainEvent;

@Named
public class GameEventHandler implements DomainEventSubscriber<GameEvent> {
   
    @Resource
    private ExternalEventPublisher externalEventPublisher;

    @Override
    public boolean subscribedTo(DomainEvent event) {
    	//PlayerCardDealtEvent is private
        return event instanceof GameEvent && !(event instanceof PlayerCardDealtEvent);
    }


	@Override
	public void handleEvent(GameEvent event) {
		externalEventPublisher.publish(new ExternalDomainEvent(event, event.getTableID()));
	}

}
