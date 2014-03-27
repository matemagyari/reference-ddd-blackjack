package org.home.blackjack.core.app.dto;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.home.blackjack.core.domain.shared.PlayerID;

public abstract class QueryResponse {
	
	private final PlayerID playerId;

	public QueryResponse(PlayerID playerId) {
		this.playerId = playerId;
	}
	
	public PlayerID getPlayerId() {
		return playerId;
	}
	
	@Override
	public boolean equals(final Object other) {
		return EqualsBuilder.reflectionEquals(this, other);
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}
}
