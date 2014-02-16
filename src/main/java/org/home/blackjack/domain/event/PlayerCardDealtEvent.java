package org.home.blackjack.domain.event;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.home.blackjack.domain.core.Card;
import org.home.blackjack.domain.core.GameId;
import org.home.blackjack.domain.core.PlayerId;

public class PlayerCardDealtEvent extends GameEvent {

	private final PlayerId player;
	//this player has to be notified, too without revealing the card
	private final PlayerId otherPlayer;
	private final Card card;

	public PlayerCardDealtEvent(GameId gameId, int sequenceNumber, PlayerId player, PlayerId otherPlayer, Card card) {
		super(gameId, sequenceNumber);
		this.player = player;
		this.otherPlayer = otherPlayer;
		this.card = card;
	}
	
	public Card getCard() {
		return card;
	}
	public PlayerId getOtherPlayer() {
		return otherPlayer;
	}
	public PlayerId getPlayer() {
		return player;
	}
	
	@Override
	public boolean equals(Object that) {
		if (that == null) return false;
		if (!that.getClass().equals(this.getClass()))
			return false;
		PlayerCardDealtEvent castThat = (PlayerCardDealtEvent) that;
		return new EqualsBuilder()
					.appendSuper(super.equals(that))
					.append(this.player, castThat.player)
					.append(this.otherPlayer, castThat.otherPlayer)
					.append(this.card, castThat.card)
					.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder()
					.appendSuper(super.hashCode())
					.append(player)
					.append(otherPlayer)
					.append(card)
					.hashCode();
	}

	@Override
	public String toString() {
		return "PlayerCardDealtEvent [player=" + player + ", otherPlayer=" + otherPlayer + ", card=" + card + ", gameId=" + gameId + ", sequenceNumber="
				+ sequenceNumber + "]";
	}

}
