package org.home.blackjack.core.app.eventhandler;

import javax.annotation.Resource;
import javax.inject.Named;

import org.home.blackjack.core.domain.game.Dealer;
import org.home.blackjack.core.domain.table.event.TableIsFullEvent;
import org.home.blackjack.util.ddd.pattern.events.DomainEvent;
import org.home.blackjack.util.ddd.pattern.events.DomainEventSubscriber;
import org.home.blackjack.util.ddd.pattern.events.LightweightDomainEventBus;
import org.home.blackjack.util.ddd.pattern.events.SubscribableEventBus;

@Named
public class TableIsFullEventHandler implements DomainEventSubscriber<TableIsFullEvent> {
    
    @Resource
    private Dealer dealer;
	@Resource
	private GameEventHandler gameEventHandler;

    @Override
    public boolean subscribedTo(DomainEvent event) {
        return event instanceof TableIsFullEvent;
    }
    @Override
    public void handleEvent(TableIsFullEvent event) {
    	SubscribableEventBus eventBus = LightweightDomainEventBus.subscribableEventBusInstance();
		eventBus.reset();
    	eventBus.register(gameEventHandler);
		
    	dealer.startANewGameOnTable(event.tableId(), event.players());
    	
    	eventBus.flush();
    }

}
