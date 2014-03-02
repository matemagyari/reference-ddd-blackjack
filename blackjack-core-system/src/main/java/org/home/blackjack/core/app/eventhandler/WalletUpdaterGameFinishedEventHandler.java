package org.home.blackjack.core.app.eventhandler;

import javax.annotation.Resource;
import javax.inject.Named;

import org.home.blackjack.core.domain.game.event.GameFinishedEvent;
import org.home.blackjack.core.domain.wallet.Reason;
import org.home.blackjack.core.domain.wallet.WalletService;
import org.home.blackjack.util.ddd.pattern.events.DomainEvent;
import org.home.blackjack.util.ddd.pattern.events.DomainEventSubscriber;

@Named
public class WalletUpdaterGameFinishedEventHandler implements DomainEventSubscriber<GameFinishedEvent> {

	@Resource
	private WalletService walletService;

	@Override
	public boolean subscribedTo(DomainEvent event) {
		return event instanceof GameFinishedEvent;
	}

	/**
	 * For sake of simplicity we skip exception handling here.
	 */
	@Override
    public void handleEvent(GameFinishedEvent event) {
    	
		Reason reason = new Reason(event.getGameID().toString());
    	walletService.giveTheWin(event.winner(), reason);
    	walletService.takeTheLoss(event.loser(), reason);
    }
}
