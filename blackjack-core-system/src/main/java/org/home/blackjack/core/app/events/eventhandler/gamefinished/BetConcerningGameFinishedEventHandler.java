package org.home.blackjack.core.app.events.eventhandler.gamefinished;

import javax.annotation.Resource;
import javax.inject.Named;

import org.home.blackjack.core.domain.cashier.Cashier;
import org.home.blackjack.core.domain.game.event.GameFinishedEvent;

@Named
public class BetConcerningGameFinishedEventHandler extends GameFinishedEventHandler {

	@Resource
	private Cashier cashier;

	/**
	 * For sake of simplicity we skip exception handling here.
	 */
	@Override
	public void handleEvent(GameFinishedEvent event) {
		cashier.giveTheWin(event.getGameID(), event.winner());
	}
}
