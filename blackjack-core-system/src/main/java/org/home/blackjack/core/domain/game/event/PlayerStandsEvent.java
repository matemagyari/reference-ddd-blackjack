package org.home.blackjack.core.domain.game.event;

import org.home.blackjack.core.domain.game.core.GameID;
import org.home.blackjack.core.domain.shared.PlayerID;
import org.home.blackjack.core.domain.shared.TableID;

public class PlayerStandsEvent extends GameEvent {

	public PlayerStandsEvent(GameID gameID, TableID tableID, PlayerID actingPlayer, int sequenceNumber) {
		super(gameID, tableID, actingPlayer, sequenceNumber);
	}

}
