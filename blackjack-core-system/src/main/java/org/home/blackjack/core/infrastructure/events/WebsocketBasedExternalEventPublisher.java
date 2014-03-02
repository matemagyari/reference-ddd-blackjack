package org.home.blackjack.core.infrastructure.events;

import javax.inject.Named;

import org.home.blackjack.core.app.event.ExternalEventPublisher;
import org.home.blackjack.util.ddd.pattern.events.DomainEvent;

import com.google.gson.GsonBuilder;


@Named
public class WebsocketBasedExternalEventPublisher implements ExternalEventPublisher {

    private final GsonBuilder gsonBuilder = new GsonBuilder();
    
	@Override
	public void publish(DomainEvent event) {
	    String json = gsonBuilder.create().toJson(event);
	    System.err.println("Publish: " + json);
	}
    
}
