package org.home.blackjack.domain.game.event;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.home.blackjack.domain.game.core.GameID;
import org.home.blackjack.util.ddd.pattern.DomainEvent;

/**
 * Domain Event (Value Object)
 * 
 * @author Mate
 * 
 */
public abstract class GameEvent implements DomainEvent {

	protected final GameID gameID;
	protected final int sequenceNumber;

	public GameEvent(GameID gameID, int sequenceNumber) {
		Validate.notNull(gameID);
		this.sequenceNumber = sequenceNumber;
		this.gameID = gameID;
	}

	public GameID getGameID() {
		return gameID;
	}

	public int getSequenceNumber() {
		return sequenceNumber;
	}

	@Override
	public boolean equals(Object that) {
		if (that == null)
			return false;
		if (!that.getClass().equals(this.getClass()))
			return false;
		GameEvent castThat = (GameEvent) that;
		return new EqualsBuilder().append(this.gameID, castThat.gameID)
				.append(this.sequenceNumber, castThat.sequenceNumber).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(gameID).append(sequenceNumber).hashCode();
	}

}
