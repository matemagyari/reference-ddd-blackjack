package org.home.blackjack.domain.game.event;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.home.blackjack.domain.game.core.GameID;
import org.home.blackjack.domain.player.PlayerID;

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
	public boolean equals(Object that) {
		if (that == null)
			return false;
		if (!that.getClass().equals(this.getClass()))
			return false;
		GameFinishedEvent castThat = (GameFinishedEvent) that;
		return new EqualsBuilder().appendSuper(super.equals(that)).append(this.winner, castThat.winner).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().appendSuper(super.hashCode()).append(winner).hashCode();
	}

	@Override
	public String toString() {
		return "GameFinishedEvent [winner=" + winner + ", gameID=" + gameID + ", sequenceNumber=" + sequenceNumber
				+ "]";
	}

}
