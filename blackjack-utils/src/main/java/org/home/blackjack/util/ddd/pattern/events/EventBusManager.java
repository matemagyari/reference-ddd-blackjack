package org.home.blackjack.util.ddd.pattern.events;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

public class EventBusManager implements DomainEventPublisherFactory {
	
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

	@Override
    public DomainEventPublisher domainEventPublisherInstance() {
        return instance.get();
    }

    private LightweightDomainEventBus subscribableEventBusInstance() {
        return instance.get();
    }

    @Resource
    public void setApplicationContext(ApplicationContext applicationContext){
        context = applicationContext;
    }
}
