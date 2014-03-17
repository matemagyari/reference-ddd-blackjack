package org.home.blackjack.core.app.service.query;

import javax.annotation.Resource;
import javax.inject.Named;

import org.home.blackjack.core.domain.game.Game;
import org.home.blackjack.core.domain.game.GameRepository;
import org.home.blackjack.core.domain.game.view.PlayerGameView;
import org.home.blackjack.core.domain.shared.PlayerID;
import org.home.blackjack.core.domain.shared.TableID;
import org.home.blackjack.core.domain.table.TableRepository;

@Named
public class QueryingApplicationService {

	@Resource
	private TableRepository tableRepository;
	@Resource
	private GameRepository gameRepository;
	
	public PrivateGameViewDTO readMyGame(TableID tableId, PlayerID playerID) {
		Game game = gameRepository.find(tableId);
		
		PlayerGameView playerGameView = null;
		return null;
	}
}
