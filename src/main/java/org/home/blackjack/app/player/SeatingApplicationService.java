package org.home.blackjack.app.player;

import javax.inject.Inject;

import org.home.blackjack.domain.common.events.SubscribableEventBus;
import org.home.blackjack.domain.shared.PlayerID;
import org.home.blackjack.domain.table.SeatingService;

public class SeatingApplicationService {
	
	private SeatingService seatingService;
	@Inject
	private SubscribableEventBus eventBuffer;
	
	public void seatPlayer(PlayerID playerID) {
		seatingService.seatPlayer(playerID);
	}

}
