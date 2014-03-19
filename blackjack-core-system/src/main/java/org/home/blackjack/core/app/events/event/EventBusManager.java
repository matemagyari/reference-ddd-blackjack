package org.home.blackjack.core.app.events.event;

import java.util.List;

import javax.annotation.Resource;
import javax.inject.Named;

import org.home.blackjack.util.ddd.pattern.events.DomainEventSubscriber;
import org.home.blackjack.util.ddd.pattern.events.LightweightDomainEventBus;

@Named
public class EventBusManager {

	@Resource
	private List<DomainEventSubscriber> subscribers;

	public void initialize() {
		LightweightDomainEventBus.subscribableEventBusInstance().reset();
		LightweightDomainEventBus.subscribableEventBusInstance().register(subscribers);
	}
	
	public void flush() {
		LightweightDomainEventBus.subscribableEventBusInstance().flush();
	}

}
