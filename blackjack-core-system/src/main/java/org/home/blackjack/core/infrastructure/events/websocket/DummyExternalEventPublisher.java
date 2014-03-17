package org.home.blackjack.core.infrastructure.events.websocket;

import javax.inject.Named;

import org.home.blackjack.core.app.events.external.ExternalDomainEvent;
import org.home.blackjack.core.app.events.external.ExternalEventPublisher;

@Named
public class DummyExternalEventPublisher implements ExternalEventPublisher {
    

    @Override
    public void publish(ExternalDomainEvent event) {
    }

}
