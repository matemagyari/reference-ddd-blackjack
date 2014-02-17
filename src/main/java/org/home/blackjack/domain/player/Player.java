package org.home.blackjack.domain.player;

import javax.inject.Inject;

import org.home.blackjack.domain.AggregateRoot;
import org.home.blackjack.domain.common.EventBus;
import org.home.blackjack.domain.game.Game;

/**
 * An aggregate root of a single entity. It is eventually consistent with the aggregated content of {@link Game}
 * entities. From the state of a {@link Game} instance, the result can be derived, so for any player the winNumber of
 * {@link Player} should equal the number of {@link Game}-s she has won.
 * 
 * But eventual consistency is enough.
 * 
 * @author Mate
 * 
 */
public final class Player extends AggregateRoot<PlayerID> {

	private PlayerScore score;

	@Inject
	private static EventBus eventBus;

	public Player(final PlayerID id) {

		super(id);
		this.score = new PlayerScore();
	}

	public void recordWin() {

		score = score.increment();
	}

	public int getWinNumber() {

		return score.wonGamesCount();
	}
}
