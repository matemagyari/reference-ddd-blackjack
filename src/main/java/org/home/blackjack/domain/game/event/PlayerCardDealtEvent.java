package org.home.blackjack.domain.game.event;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.home.blackjack.domain.game.core.Card;
import org.home.blackjack.domain.game.core.GameID;
import org.home.blackjack.domain.player.PlayerID;

public class PlayerCardDealtEvent extends GameEvent {

	private final PlayerID player;
	// this player has to be notified, too without revealing the card
	private final PlayerID otherPlayer;
	private final Card card;

	public PlayerCardDealtEvent(GameID gameID, int sequenceNumber, PlayerID player, PlayerID otherPlayer, Card card) {
		super(gameID, sequenceNumber);
		Validate.notNull(player);
		Validate.notNull(otherPlayer);
		Validate.notNull(card);
		this.player = player;
		this.otherPlayer = otherPlayer;
		this.card = card;
	}

	public Card getCard() {
		return card;
	}

	public PlayerID getOtherPlayer() {
		return otherPlayer;
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
		PlayerCardDealtEvent castThat = (PlayerCardDealtEvent) that;
		return new EqualsBuilder().appendSuper(super.equals(that)).append(this.player, castThat.player)
				.append(this.otherPlayer, castThat.otherPlayer).append(this.card, castThat.card).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().appendSuper(super.hashCode()).append(player).append(otherPlayer).append(card)
				.hashCode();
	}

	@Override
	public String toString() {
		return "PlayerCardDealtEvent [player=" + player + ", otherPlayer=" + otherPlayer + ", card=" + card
				+ ", gameID=" + gameID + ", sequenceNumber=" + sequenceNumber + "]";
	}

}
