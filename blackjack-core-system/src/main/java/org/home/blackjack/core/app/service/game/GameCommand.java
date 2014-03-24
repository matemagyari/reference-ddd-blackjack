package org.home.blackjack.core.app.service.game;

import org.home.blackjack.core.domain.game.core.GameID;
import org.home.blackjack.core.domain.shared.PlayerID;

public class GameCommand {
	
	private final String action;
	private final String gameId;
	private final String playerId;

	public GameCommand(String playerId, String gameId, String action) {
		this.playerId = playerId;
		this.gameId = gameId;
		this.action = action;
	}
	
	public GameID getGameID() {
		return GameID.createFrom(gameId);
	}
	public PlayerID getPlayerId() {
		return PlayerID.createFrom(playerId);
	}
	
	public GameActionType getAction() {
		return GameActionType.valueOf(action);
	}

	@Override
	public String toString() {
		return "SeatingCommand [gameId=" + gameId + ", playerId=" + playerId + "]";
	}
}
