package org.home.blackjack.domain.game.event;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.home.blackjack.domain.game.core.GameID;
import org.home.blackjack.domain.player.PlayerID;

public class PlayerStandsEvent extends GameEvent {

	private final PlayerID player;

	public PlayerStandsEvent(GameID gameID, int sequenceNumber, PlayerID player) {
		super(gameID, sequenceNumber);
		Validate.notNull(player);
		this.player = player;
	}

	public PlayerID getPlayer() {
		return player;
	}

	@Override
	public boolean equals(Object that) {
		if (that == null)
			return false;
		if (!that.getClass().equals(this.getClass()))
			return false;
		PlayerStandsEvent castThat = (PlayerStandsEvent) that;
		return new EqualsBuilder().appendSuper(super.equals(that)).append(this.player, castThat.player).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().appendSuper(super.hashCode()).append(player).hashCode();
	}

	@Override
	public String toString() {
		return "PlayerStandsEvent [player=" + player + ", gameID=" + gameID + ", sequenceNumber=" + sequenceNumber
				+ "]";
	}

}
