package org.home.blackjack.app.service;

import org.home.blackjack.domain.core.GameId;
import org.home.blackjack.domain.core.PlayerId;

/**
 * Value Object
 * @author Mate
 *
 */
public class GameAction {
	
	private GameId gameId;
	private PlayerId player;
	private GameActionType actionType;

	public GameAction(GameId gameId, PlayerId player, GameActionType actionType) {
		this.gameId = gameId;
		this.player = player;
		this.actionType = actionType;
	}
	
	public GameActionType getActionType() {
		return actionType;
	}
	public PlayerId getPlayer() {
		return player;
	}
	public GameId getGameId() {
		return gameId;
	}

}
