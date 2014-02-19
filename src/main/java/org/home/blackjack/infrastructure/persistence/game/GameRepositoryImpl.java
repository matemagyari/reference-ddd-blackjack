package org.home.blackjack.infrastructure.persistence.game;

import javax.annotation.Resource;
import javax.inject.Named;

import org.home.blackjack.domain.game.Game;
import org.home.blackjack.domain.game.GameRepository;
import org.home.blackjack.domain.game.core.GameID;

@Named
public class GameRepositoryImpl implements GameRepository {
	
	@Resource
	private GameStore gameStore;

	@Override
	public Game find(GameID gameID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Game game) {
		// TODO Auto-generated method stub
		
	}

	
}
