package org.home.blackjack.core.domain.player;

import org.home.blackjack.core.domain.game.Game;
import org.home.blackjack.core.domain.player.core.PlayerName;
import org.home.blackjack.core.domain.shared.PlayerID;
import org.home.blackjack.util.ddd.pattern.domain.AggregateRoot;

/**
 * An aggregate root of a single entity. It is eventually consistent with the aggregated content of {@link Game}
 * entities. From the state of a {@link Game} instance, the result can be derived, so for any player the winNumber of
 * {@link Player} should equal the number of {@link Game}-s she has won.
 * 
 * But eventual consistency is enough.
 * 
 * This {@link Player} is not the same as {@link org.home.blackjack.core.domain.game.Player}. They are two different
 * aspects of a player.
 * 
 * @author Mate
 * 
 */
public class Player extends AggregateRoot<PlayerID> {

	private final PlayerName name;
	private int winNumber = 0;

	public Player(final PlayerID id, final PlayerName name) {
		super(id);
		this.name = name;
	}

	public void recordWin() {
		winNumber++;
	}

	public int getWinNumber() {
		return winNumber;
	}

	public PlayerName getName() {
		return name;
	}
}
