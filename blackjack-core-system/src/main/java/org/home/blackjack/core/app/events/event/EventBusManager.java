package org.home.blackjack.core.app.events.event;

import java.util.List;

import javax.annotation.Resource;
import javax.inject.Named;

import org.apache.log4j.Logger;
import org.home.blackjack.util.ddd.pattern.events.DomainEventSubscriber;
import org.home.blackjack.util.ddd.pattern.events.LightweightDomainEventBus;

@Named
public class EventBusManager {
	
	private static Logger LOGGER = Logger.getLogger(EventBusManager.class);

	@Resource
	private List<DomainEventSubscriber> subscribers;

	public void initialize() {
		LOGGER.info("initialize");
		LightweightDomainEventBus.subscribableEventBusInstance().reset();
		LightweightDomainEventBus.subscribableEventBusInstance().register(subscribers);
	}
	
	public void flush() {
		LOGGER.info("flush");
		LightweightDomainEventBus.subscribableEventBusInstance().flush();
	}

}
