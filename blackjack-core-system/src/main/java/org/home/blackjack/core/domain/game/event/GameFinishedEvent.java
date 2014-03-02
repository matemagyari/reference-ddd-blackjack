package org.home.blackjack.core.domain.game.event;

import org.home.blackjack.core.domain.game.core.GameID;
import org.home.blackjack.core.domain.shared.PlayerID;
import org.home.blackjack.core.domain.shared.TableID;
import org.home.blackjack.util.ddd.util.Validator;

public class GameFinishedEvent extends GameEvent {

	private final PlayerID winner;
	private TableID tableID;
	private PlayerID loser;

	public GameFinishedEvent(GameID gameID, TableID tableID, int sequenceNumber, PlayerID winner, PlayerID loser) {
		super(gameID, sequenceNumber);
		Validator.notNull(gameID, tableID, winner, loser);
		this.tableID = tableID;
		this.winner = winner;
		this.loser = loser;
	}

	public PlayerID winner() {
		return winner;
	}
	public PlayerID loser() {
		return loser;
	}
	
	public TableID tableID() {
		return tableID;
	}

	@Override
	public String toString() {
		return "GameFinishedEvent [winner=" + winner + ", tableID=" + tableID + ", gameID=" + gameID + ", sequenceNumber=" + sequenceNumber + "]";
	}

}
