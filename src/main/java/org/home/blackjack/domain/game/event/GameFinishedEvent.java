package org.home.blackjack.domain.game.event;

import org.apache.commons.lang3.Validate;
import org.home.blackjack.domain.game.core.GameID;
import org.home.blackjack.domain.shared.PlayerID;

public class GameFinishedEvent extends GameEvent {

	private final PlayerID winner;

	public GameFinishedEvent(GameID gameID, int sequenceNumber, PlayerID winner) {
		super(gameID, sequenceNumber);
		Validate.notNull(winner);
		this.winner = winner;
	}

	public PlayerID getWinner() {
		return winner;
	}

	@Override
	public String toString() {
		return "GameFinishedEvent [winner=" + winner + ", gameID=" + gameID + ", sequenceNumber=" + sequenceNumber
				+ "]";
	}

}
