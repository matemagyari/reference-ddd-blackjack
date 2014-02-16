package org.home.blackjack.domain.game.event;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.home.blackjack.domain.game.core.GameId;
import org.home.blackjack.domain.shared.PlayerId;

public class GameFinishedEvent extends GameEvent {

	private final PlayerId winner;

	public GameFinishedEvent(GameId gameId, int sequenceNumber, PlayerId winner) {
		super(gameId, sequenceNumber);
		Validate.notNull(winner);
		this.winner = winner;
	}
	
	public PlayerId getWinner() {
		return winner;
	}
	
	@Override
	public boolean equals(Object that) {
		if (that == null) return false;
		if (!that.getClass().equals(this.getClass()))
			return false;
		GameFinishedEvent castThat = (GameFinishedEvent) that;
		return new EqualsBuilder()
					.appendSuper(super.equals(that))
					.append(this.winner, castThat.winner)
					.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder()
					.appendSuper(super.hashCode())
					.append(winner)
					.hashCode();
	}

	@Override
	public String toString() {
		return "GameFinishedEvent [winner=" + winner + ", gameId=" + gameId + ", sequenceNumber=" + sequenceNumber + "]";
	}

}
