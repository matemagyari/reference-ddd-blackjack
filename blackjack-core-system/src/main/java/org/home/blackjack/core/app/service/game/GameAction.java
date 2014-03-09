package org.home.blackjack.core.app.service.game;

import org.home.blackjack.core.domain.game.core.GameID;
import org.home.blackjack.core.domain.shared.PlayerID;

/**
 * App Value Object.
 * @author Mate
 *
 */
public class GameAction {
	
	private final GameID gameID;
	private final PlayerID playerID;
	private final GameActionType gameActionType;

	public GameAction(GameID gameID, PlayerID playerID, GameActionType gameActionType) {
		this.gameID = gameID;
		this.playerID = playerID;
		this.gameActionType = gameActionType;
	}
	
	public GameActionType getGameActionType() {
		return gameActionType;
	}
	public PlayerID getPlayerID() {
		return playerID;
	}
	public GameID getGameID() {
		return gameID;
	}
}
