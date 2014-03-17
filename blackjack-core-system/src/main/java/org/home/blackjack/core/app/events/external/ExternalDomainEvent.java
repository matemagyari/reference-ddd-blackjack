package org.home.blackjack.core.app.events.external;

import org.home.blackjack.core.domain.shared.PlayerID;
import org.home.blackjack.core.domain.shared.TableID;
import org.home.blackjack.util.ddd.pattern.events.DomainEvent;

/**
 * Contains the Domain event, plus routing information. Which tables and which player to notify.
 * @author Mate
 *
 */
public class ExternalDomainEvent {
	
	private final Addressee addressee;
	private final DomainEvent event;

	public ExternalDomainEvent(DomainEvent event, TableID tableId, PlayerID playerId) {
		this.event = event;
		this.addressee = new Addressee(playerId, tableId);
	}
	
	public DomainEvent getEvent() {
		return event;
	}
	public Addressee getAddressee() {
		return addressee;
	}
	
	public static class Addressee {
		public final PlayerID playerId;
		public final TableID tableId;
		public Addressee(PlayerID playerId, TableID tableId) {
			this.playerId = playerId;
			this.tableId = tableId;
		}
	}

}
