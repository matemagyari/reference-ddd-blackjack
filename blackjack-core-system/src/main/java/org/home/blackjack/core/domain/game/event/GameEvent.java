package org.home.blackjack.core.domain.game.event;

import org.home.blackjack.core.domain.game.core.GameID;
import org.home.blackjack.core.domain.shared.PlayerID;
import org.home.blackjack.core.domain.shared.TableID;
import org.home.blackjack.util.ddd.pattern.ValueObject;
import org.home.blackjack.util.ddd.pattern.events.DomainEvent;
import org.home.blackjack.util.ddd.util.Validator;

/**
 * Domain Event (Value Object)
 * 
 * @author Mate
 * 
 */
public abstract class GameEvent extends ValueObject implements DomainEvent {

	protected final TableID tableID;
	protected final GameID gameID;
	protected final PlayerID actingPlayer;
	protected final int sequenceNumber;

	public GameEvent(GameID gameID,TableID tableID,PlayerID actingPlayer, int sequenceNumber) {
		Validator.notNull(gameID, tableID);
		this.actingPlayer = actingPlayer;
		this.tableID = tableID;
		this.sequenceNumber = sequenceNumber;
		this.gameID = gameID;
	}

	public GameID getGameID() {
		return gameID;
	}

	public int getSequenceNumber() {
		return sequenceNumber;
	}
	public PlayerID getActingPlayer() {
		return actingPlayer;
	}
	public TableID getTableID() {
		return tableID;
	}
}
