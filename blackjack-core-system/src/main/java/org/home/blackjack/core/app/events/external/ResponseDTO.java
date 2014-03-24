package org.home.blackjack.core.app.events.external;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.home.blackjack.core.domain.shared.PlayerID;

public class ResponseDTO {
	
	private final PlayerID playerId;

	public ResponseDTO(PlayerID playerId) {
		this.playerId = playerId;
	}
	
	public PlayerID getPlayerId() {
		return playerId;
	}
	
	@Override
	public final boolean equals(final Object other) {
		return EqualsBuilder.reflectionEquals(this, other);
	}

	@Override
	public final int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}
}
