package org.home.blackjack.domain.game;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.home.blackjack.domain.common.DomainException;
import org.home.blackjack.domain.game.core.Card;
import org.home.blackjack.domain.game.core.Card.Rank;
import org.home.blackjack.domain.shared.PlayerId;

import com.google.common.collect.Sets;

/**
 * Entity inside the {@link GameImpl} aggregate root. It's id ({@link PlayerId}) is only unique inside the aggregate.
 * Nothing can reference it outside of {@link GameImpl}, otherwise calling isDealtWith could violate the invariant
 * 
 * It could have also been implemented as a Value Object, should we choose to implement 'deal' in the following way
 * 	public PlayerHand deal(Card card) {
 *      Set<Card> newCards = Sets.newHashSet(this.cards);
 *      newCards.add(card);
 *		return new PlayerHand(playerId, newCards);
 *	}
 *	In that case it would be handled differently in {@link GameImpl}.
 * @author Mate
 *
 */
class PlayerHand {
	
	private final PlayerId playerId;
	private final Set<Card> cards;
	private boolean stopped;
	
	public static PlayerHand createEmptyFor(PlayerId playerId) {
		return new PlayerHand(playerId, new HashSet<Card>());
	}
	
	public static PlayerHand createStarterFor(PlayerId playerId, Card card1, Card card2) {
		return new PlayerHand(playerId, Sets.newHashSet(card1, card2));
	}
	
	private PlayerHand(PlayerId playerId, Set<Card> cards) {
		Validate.notNull(playerId);
		Validate.notNull(cards);
		this.playerId = playerId;
		this.cards = cards;
	}
	
	public boolean isOf(PlayerId aPlayerId) {
		return playerId.equals(aPlayerId);
	}

	public int isDealtWith(Card card) {
		if (stopped) {
			throw new DomainException(playerId + " is dealt a card after stop");
		}
		assert !cards.contains(card);
		this.cards.add(card);
		return score();
	}
	
	public PlayerId getPlayerId() {
		return playerId;
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
	 * @return
	 */
	public int score() {
		int score = 0;
		int aceCounter = 0;
		for (Card card : cards) {
			score += card.value();
			if (card.rank == Rank.ACE) {
				aceCounter ++;
			}
		}
		//if player has two ACES, then one has a value of 1
		if (aceCounter > 1) {
			score -= 10;
		}
		
		return score;
	}
	
	@Override
	public boolean equals(Object that) {
		if (that == null) return false;
		if (!(that instanceof PlayerHand))
			return false;
		PlayerHand castThat = (PlayerHand) that;
		return new EqualsBuilder()
					.append(this.playerId, castThat.playerId)
					.append(this.cards, castThat.cards)
					.append(this.stopped, castThat.stopped)
					.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder()
					.append(playerId)
					.append(cards)
					.append(stopped)
					.hashCode();
	}

	@Override
	public String toString() {
		return "PlayerHand [playerId=" + playerId + ", cards=" + cards + ", stopped=" + stopped + "]";
	}

}
