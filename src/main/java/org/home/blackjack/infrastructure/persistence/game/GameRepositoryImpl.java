package org.home.blackjack.infrastructure.persistence.game;

import javax.inject.Named;

import org.home.blackjack.domain.game.Game;
import org.home.blackjack.domain.game.GameRepository;
import org.home.blackjack.domain.game.core.GameID;
import org.home.blackjack.infrastructure.persistence.shared.PersistenceAssembler;
import org.home.blackjack.infrastructure.persistence.shared.PersistenceObject;

@Named
public class GameRepositoryImpl implements GameRepository {
	
	private final GameStore gameStore;
	private final PersistenceAssembler<Game, PersistenceObject<Game>> gameStoreAssembler;
	
	public GameRepositoryImpl(GameStore gameStore) {
		this.gameStore = gameStore;
		this.gameStoreAssembler = gameStore.assembler();
	}
	
	@Override
	public Game find(GameID gameID) {
		PersistenceObject<Game> game = gameStore.find(gameStoreAssembler.toPersistence(gameID));
		return gameStoreAssembler.toDomain(game);
	}

	@Override
	public void update(Game game) {
		PersistenceObject<Game> po = gameStoreAssembler.toPersistence(game);
		gameStore.update(po);
	}

	
}
