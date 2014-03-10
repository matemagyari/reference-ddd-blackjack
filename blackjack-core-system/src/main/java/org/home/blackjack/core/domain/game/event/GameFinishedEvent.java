package org.home.blackjack.core.domain.game.event;

import org.home.blackjack.core.domain.game.core.GameID;
import org.home.blackjack.core.domain.shared.PlayerID;
import org.home.blackjack.core.domain.shared.TableID;

public class GameFinishedEvent extends GameEvent {

	public GameFinishedEvent(GameID gameID, TableID tableID, int sequenceNumber, PlayerID winner, PlayerID loser) {
		super(gameID, tableID, winner, sequenceNumber);
	}
	
	public PlayerID winner() {
		return actingPlayer;
	}

}
