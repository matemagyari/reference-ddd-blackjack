package org.home.blackjack.domain.game.event;

import org.home.blackjack.domain.common.Validator;
import org.home.blackjack.domain.game.core.GameID;
import org.home.blackjack.domain.shared.PlayerID;
import org.home.blackjack.domain.table.core.TableID;

public class GameFinishedEvent extends GameEvent {

	private final PlayerID winner;
	private TableID tableID;

	public GameFinishedEvent(GameID gameID, TableID tableID, int sequenceNumber, PlayerID winner) {
		super(gameID, sequenceNumber);
		Validator.notNull(gameID, tableID);
		this.tableID = tableID;
		this.winner = winner;
	}

	public PlayerID winner() {
		return winner;
	}
	
	public TableID tableID() {
		return tableID;
	}

	@Override
	public String toString() {
		return "GameFinishedEvent [winner=" + winner + ", tableID=" + tableID + ", gameID=" + gameID + ", sequenceNumber=" + sequenceNumber + "]";
	}

}
