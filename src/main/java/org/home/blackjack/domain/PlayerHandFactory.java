package org.home.blackjack.domain;

/**
 * Factory
 * @author Mate
 *
 */
public class PlayerHandFactory {
	
	public PlayerHand createEmptyFor(PlayerId playerId) {
		return PlayerHand.createEmptyFor(playerId);
	}
	

}
