package org.home.blackjack.domain.player;

import org.home.blackjack.domain.shared.PlayerId;

/**
 * An aggregate root of a single entity. It is eventually consistent with the
 * aggregated content of {@link GameImpl} entities. From the state of a {@link GameImpl}
 * instance, the result can be derived, so for any player the winNumber of
 * {@link Player} should equal the number of {@link GameImpl}-s she has won.
 * 
 * But eventual consistency is enough.
 * 
 * @author Mate
 * 
 */
public class Player {

	private final PlayerId id;
	private final PlayerName name;
	private int winNumber = 0;

	public Player(PlayerId  id, PlayerName name) {
		this.id = id;
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

	public PlayerId getId() {
		return id;
	}

}
