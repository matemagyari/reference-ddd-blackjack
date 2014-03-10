package org.home.blackjack.core.app.event;

import org.home.blackjack.core.domain.shared.PlayerID;
import org.home.blackjack.core.domain.shared.TableID;
import org.home.blackjack.util.ddd.pattern.events.DomainEvent;

/**
 * Contains the Domain event, plus routing information. Which tables and which player to notify.
 * @author Mate
 *
 */
public class ExternalDomainEvent {
	
	private final PlayerID playerId;
	private final TableID tableId;
	private final DomainEvent event;

	public ExternalDomainEvent(DomainEvent event, TableID tableId, PlayerID playerId) {
		this.event = event;
		this.tableId = tableId;
		this.playerId = playerId;
	}
	
	public DomainEvent getEvent() {
		return event;
	}
	public PlayerID getPlayerId() {
		return playerId;
	}
	public TableID getTableId() {
		return tableId;
	}

}
