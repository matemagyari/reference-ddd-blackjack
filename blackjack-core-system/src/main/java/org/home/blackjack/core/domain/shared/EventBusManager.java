package org.home.blackjack.core.domain.shared;

import javax.annotation.Resource;
import javax.inject.Named;

import org.apache.log4j.Logger;
import org.home.blackjack.util.ddd.pattern.events.DomainEventPublisher;
import org.home.blackjack.util.ddd.pattern.events.LightweightDomainEventBus;
import org.home.blackjack.util.ddd.pattern.events.SubscribableEventBus;
import org.springframework.context.ApplicationContext;

@Named
public class EventBusManager {
	
	private static Logger LOGGER = Logger.getLogger(EventBusManager.class);

    private static final ThreadLocal<LightweightDomainEventBus> instance = new ThreadLocal<LightweightDomainEventBus>() {
        protected LightweightDomainEventBus initialValue() {
            return context.getBean(LightweightDomainEventBus.class);
        }
    };

    private static ApplicationContext context;

	public void initialize() {
        subscribableEventBusInstance().reset();
	}
	
	public void flush() {
		LOGGER.info("flush");
        subscribableEventBusInstance().flush();
	}

    public DomainEventPublisher domainEventPublisherInstance() {
        return instance.get();
    }

    public SubscribableEventBus subscribableEventBusInstance() {
        return instance.get();
    }

    @Resource
    public void setApplicationContext(ApplicationContext applicationContext){
        context = applicationContext;
    }
}
