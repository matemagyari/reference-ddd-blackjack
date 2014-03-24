package org.home.blackjack.core.app.events.eventhandler;

import javax.annotation.Resource;
import javax.inject.Named;

import org.home.blackjack.core.app.events.external.ExternalDomainEvent;
import org.home.blackjack.core.app.events.external.ExternalEventPublisher;
import org.home.blackjack.core.domain.game.event.PlayerCardDealtEvent;
import org.home.blackjack.util.ddd.pattern.events.DomainEvent;
import org.home.blackjack.util.ddd.pattern.events.DomainEventSubscriber;

@Named
public class PlayerCardDealtEventHandler implements DomainEventSubscriber<PlayerCardDealtEvent> {
   
    @Resource
    private ExternalEventPublisher externalEventPublisher;

    @Override
    public boolean subscribedTo(DomainEvent event) {
        return event instanceof PlayerCardDealtEvent;
    }

	@Override
	public void handleEvent(PlayerCardDealtEvent event) {
		externalEventPublisher.publish(new ExternalDomainEvent(event, event.getTableID(), event.getActingPlayer()));
		PublicPlayerCardDealtEvent publicPlayerCardDealtEvent = PublicPlayerCardDealtEvent.from(event);
		externalEventPublisher.publish(new ExternalDomainEvent(publicPlayerCardDealtEvent, publicPlayerCardDealtEvent.getTableID()));
	}

}
