package org.home.blackjack.core.domain.game;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.Validate;
import org.home.blackjack.core.domain.game.core.Card;
import org.home.blackjack.core.domain.game.core.Card.Rank;
import org.home.blackjack.core.domain.shared.PlayerID;
import org.home.blackjack.util.ddd.pattern.domain.Entity;
import org.home.blackjack.util.ddd.util.DomainException;

import com.google.common.collect.Lists;

/**
 * Entity inside the {@link Game} aggregate root. It's id (
 * {@link org.home.blackjack.core.domain.shared.PlayerID}) is only unique inside the
 * aggregate. Nothing can reference it outside of {@link Game}, otherwise
 * calling isDealtWith could violate the invariant
 * 
 * This {@link Player} is not the same as
 * {@link org.home.blackjack.core.domain.player.Player}. They are two different
 * aspects of a player.
 * 
 * 
 * 
 * @author Mate
 * 
 */
class Player extends Entity<PlayerID> {

    private final List<Card> cards;
    private boolean stopped;

    public static Player createEmptyFor(PlayerID playerID) {
        return new Player(playerID, new ArrayList<Card>());
    }

    public static Player createStarterFor(PlayerID playerID, Card card1, Card card2) {
        return new Player(playerID, Lists.newArrayList(card1, card2));
    }
    

    private Player(PlayerID playerID, List<Card> cards) {
    	super(playerID);
        Validate.notNull(playerID);
        Validate.notNull(cards);
        this.cards = cards;
    }

    public boolean isOf(PlayerID aPlayerID) {
        return getID().equals(aPlayerID);
    }

    public int isDealtWith(Card card) {
        if (stopped) {
            throw new DomainException(getID() + " is dealt a card after stop");
        }
        assert !cards.contains(card);
        this.cards.add(card);
        return score();
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
     * Important that the logic should be where the data is. This yields rich
     * object model.
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
    public String toString() {
        return "Player [getID()=" + getID() + ", cards=" + cards + ", stopped=" + stopped + "]";
    }

}
