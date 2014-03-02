package org.home.blackjack.core.domain.player;

import org.home.blackjack.util.ddd.pattern.ValueObject;

/**
 * The score of a player playing Black Jack.
 * 
 * @author michele.sollecito
 */
public final class PlayerScore extends ValueObject {

	private final int wonGamesCount;

	protected PlayerScore increment() {

		return new PlayerScore(wonGamesCount + 1);
	}

	protected PlayerScore() {

		this.wonGamesCount = 0;
	}

	private PlayerScore(final int wonGamesCount) {

		this.wonGamesCount = wonGamesCount;
	}

	public int wonGamesCount() {

		return wonGamesCount;
	}
}
