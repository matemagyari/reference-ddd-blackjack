package org.home.blackjack.domain.game.event;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.home.blackjack.domain.core.GameId;
import org.home.blackjack.domain.event.DomainEvent;

/**
 * Domain Event (Value Object)
 * @author Mate
 *
 */
public abstract class GameEvent implements DomainEvent {
	
	protected final GameId gameId;
	protected final int sequenceNumber;

	//TODO check that it's not null
	public GameEvent(GameId gameId, int sequenceNumber) {
		Validate.notNull(gameId);
		this.sequenceNumber = sequenceNumber;
		this.gameId = gameId;
	}
	
	public GameId getGameId() {
		return gameId;
	}
	public int getSequenceNumber() {
		return sequenceNumber;
	}
	
	@Override
	public boolean equals(Object that) {
		if (that == null) return false;
		if (!that.getClass().equals(this.getClass()))
			return false;
		GameEvent castThat = (GameEvent) that;
		return new EqualsBuilder()
					.append(this.gameId, castThat.gameId)
					.append(this.sequenceNumber, castThat.sequenceNumber)
					.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder()
					.append(gameId)
					.append(sequenceNumber)
					.hashCode();
	}

}
