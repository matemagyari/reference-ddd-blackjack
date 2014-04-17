package org.home.blackjack.util.ddd.pattern.app.event;

import javax.annotation.Resource;

import org.home.blackjack.util.ddd.pattern.domain.events.DomainEventPublisher;
import org.home.blackjack.util.ddd.pattern.domain.events.DomainEventPublisherFactory;
import org.home.blackjack.util.ddd.pattern.infrastructure.event.LightweightDomainEventBus;
import org.springframework.context.ApplicationContext;

public class EventBusManager implements DomainEventPublisherFactory {
	
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
        subscribableEventBusInstance().flush(this);
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
