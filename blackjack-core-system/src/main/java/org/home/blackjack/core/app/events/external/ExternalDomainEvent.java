package org.home.blackjack.core.app.events.external;

import org.home.blackjack.core.domain.player.event.LeaderBoardChangedEvent;
import org.home.blackjack.core.domain.shared.PlayerID;
import org.home.blackjack.core.domain.shared.TableID;
import org.home.blackjack.util.ddd.pattern.domain.model.DomainEvent;

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
	public ExternalDomainEvent(DomainEvent event, TableID tableId) {
		this(event, tableId, null);
	}
	
	public ExternalDomainEvent(LeaderBoardChangedEvent event) {
	    this(event, null, null);
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
		public final boolean leaderboard;
		public Addressee(PlayerID playerId, TableID tableId) {
			this.playerId = playerId;
			this.tableId = tableId;
			this.leaderboard = false;
		}
		public Addressee() {
		    this.playerId = null;
		    this.tableId = null;
		    this.leaderboard = true;
		}
		@Override
		public String toString() {
			return "Addressee [playerId=" + playerId + ", tableId=" + tableId + "]";
		}
	}

	@Override
	public String toString() {
		return "ExternalDomainEvent [addressee=" + addressee + ", event=" + event + "]";
	}
	
}
