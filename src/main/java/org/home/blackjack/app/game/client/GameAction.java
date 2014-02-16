package org.home.blackjack.app.game.client;

import org.apache.commons.lang3.Validate;
import org.home.blackjack.domain.core.GameId;
import org.home.blackjack.domain.core.PlayerId;

/**
 * App Value Object
 * @author Mate
 *
 */
public class GameAction {
	
	private GameId gameId;
	private PlayerId player;
	private GameActionType actionType;

	public GameAction(GameId gameId, PlayerId player, GameActionType actionType) {
		Validate.notNull(gameId);
		Validate.notNull(player);
		Validate.notNull(actionType);
		this.gameId = gameId;
		this.player = player;
		this.actionType = actionType;
	}
	
	public GameActionType getType() {
		return actionType;
	}
	public PlayerId getPlayer() {
		return player;
	}
	public GameId getGameId() {
		return gameId;
	}

}
