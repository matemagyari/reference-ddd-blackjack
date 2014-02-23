package org.home.blackjack.domain.game.event;

import org.apache.commons.lang3.Validate;
import org.home.blackjack.domain.game.core.GameID;
import org.home.blackjack.util.ddd.pattern.DomainEvent;
import org.home.blackjack.util.ddd.pattern.ValueObject;

/**
 * Domain Event (Value Object)
 * 
 * @author Mate
 * 
 */
public abstract class GameEvent extends ValueObject implements DomainEvent {

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
}
