package org.home.blackjack.core.app.events.eventhandler;

import javax.annotation.Resource;
import javax.inject.Named;

import org.home.blackjack.core.domain.cashier.Cashier;
import org.home.blackjack.core.domain.game.event.GameFinishedEvent;
import org.home.blackjack.util.ddd.pattern.events.DomainEvent;
import org.home.blackjack.util.ddd.pattern.events.DomainEventSubscriber;

@Named
public class BetConcerningGameFinishedEventHandler implements DomainEventSubscriber<GameFinishedEvent> {

	@Resource
	private Cashier cashier;

	@Override
	public boolean subscribedTo(DomainEvent event) {
		return event instanceof GameFinishedEvent;
	}

	/**
	 * For sake of simplicity we skip exception handling here.
	 */
	@Override
	public void handleEvent(GameFinishedEvent event) {

		cashier.giveTheWin(event.getGameID(), event.winner());
	}
}
