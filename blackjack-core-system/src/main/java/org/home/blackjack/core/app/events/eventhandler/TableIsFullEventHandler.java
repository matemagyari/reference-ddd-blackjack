package org.home.blackjack.core.app.events.eventhandler;

import javax.annotation.Resource;
import javax.inject.Named;

import org.home.blackjack.core.domain.Dealer;
import org.home.blackjack.core.domain.table.event.TableIsFullEvent;
import org.home.blackjack.util.ddd.pattern.events.DomainEvent;
import org.home.blackjack.util.ddd.pattern.events.DomainEventSubscriber;

@Named
public class TableIsFullEventHandler implements DomainEventSubscriber<TableIsFullEvent> {

	@Resource
	private Dealer dealer;

	@Override
	public boolean subscribedTo(DomainEvent event) {
		return event instanceof TableIsFullEvent;
	}

	@Override
	public void handleEvent(TableIsFullEvent event) {
		dealer.startANewGameOnTable(event.tableId(), event.players());
	}

}
