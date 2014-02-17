package org.home.blackjack.domain.game;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.home.blackjack.domain.common.DomainException;
import org.home.blackjack.domain.game.core.Card;
import org.home.blackjack.domain.game.core.Card.Rank;
import org.home.blackjack.domain.player.PlayerID;

import com.google.common.collect.Sets;

/**
 * Entity inside the {@link Game} aggregate root. It's id ({@link org.home.blackjack.domain.player.PlayerID}) is only
 * unique inside the aggregate. Nothing can reference it outside of {@link Game}, otherwise calling isDealtWith could
 * violate the invariant
 * 
 * It could have also been implemented as a Value Object, should we choose to implement 'deal' in the following way
 * public Player deal(Card card) { Set<Card> newCards = Sets.newHashSet(this.cards); newCards.add(card); return new
 * Player(playerID, newCards); } In that case it would be handled differently in {@link Game}.
 * 
 * @author Mate
 * 
 */
class Player {

	private final PlayerID playerID;
	private final Set<Card> cards;
	private boolean stopped;

	public static Player createEmptyFor(PlayerID playerID) {
		return new Player(playerID, new HashSet<Card>());
	}

	public static Player createStarterFor(PlayerID playerID, Card card1, Card card2) {
		return new Player(playerID, Sets.newHashSet(card1, card2));
	}

	private Player(PlayerID playerID, Set<Card> cards) {
		Validate.notNull(playerID);
		Validate.notNull(cards);
		this.playerID = playerID;
		this.cards = cards;
	}

	public boolean isOf(PlayerID aPlayerID) {
		return playerID.equals(aPlayerID);
	}

	public int isDealtWith(Card card) {
		if (stopped) {
			throw new DomainException(playerID + " is dealt a card after stop");
		}
		assert !cards.contains(card);
		this.cards.add(card);
		return score();
	}

	public PlayerID getPlayerID() {
		return playerID;
	}

	public boolean notStopped() {
		return !stopped();
	}

	public boolean stopped() {
		return stopped;
	}

	public void stand() {
		this.stopped = true;
	}

	/**
	 * Important that the logic should be where the data is. This yields rich object model.
	 * 
	 * @return
	 */
	public int score() {
		int score = 0;
		int aceCounter = 0;
		for (Card card : cards) {
			score += card.value();
			if (card.rank == Rank.ACE) {
				aceCounter++;
			}
		}
		// if player has two ACES, then one has a value of 1
		if (aceCounter > 1) {
			score -= 10;
		}

		return score;
	}

	@Override
	public boolean equals(Object that) {
		if (that == null)
			return false;
		if (!(that instanceof Player))
			return false;
		Player castThat = (Player) that;
		return new EqualsBuilder().append(this.playerID, castThat.playerID).append(this.cards, castThat.cards)
				.append(this.stopped, castThat.stopped).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(playerID).append(cards).append(stopped).hashCode();
	}

	@Override
	public String toString() {
		return "Player [playerID=" + playerID + ", cards=" + cards + ", stopped=" + stopped + "]";
	}

}
