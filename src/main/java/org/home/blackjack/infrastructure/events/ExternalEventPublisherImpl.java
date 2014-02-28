package org.home.blackjack.infrastructure.events;

import javax.inject.Named;

import org.home.blackjack.app.event.ExternalEventPublisher;
import org.home.blackjack.util.ddd.pattern.events.DomainEvent;

import com.google.gson.GsonBuilder;

@Named
public class ExternalEventPublisherImpl implements ExternalEventPublisher {

    private final GsonBuilder gsonBuilder = new GsonBuilder();
    
	@Override
	public void publish(DomainEvent event) {
	    String json = gsonBuilder.create().toJson(event);
	    System.err.println("Publish: " + json);
	}

}
