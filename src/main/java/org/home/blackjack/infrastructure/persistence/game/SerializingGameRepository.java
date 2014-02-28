package org.home.blackjack.infrastructure.persistence.game;

import java.util.concurrent.locks.Lock;

import javax.annotation.Resource;
import javax.inject.Named;

import org.home.blackjack.domain.game.Game;
import org.home.blackjack.domain.game.GameRepository;
import org.home.blackjack.domain.game.core.GameID;
import org.home.blackjack.domain.game.exception.GameNotFoundException;
import org.home.blackjack.infrastructure.persistence.game.store.GameStore;
import org.home.blackjack.infrastructure.persistence.shared.PersistenceAssembler;
import org.home.blackjack.infrastructure.persistence.shared.PersistenceObject;
import org.home.blackjack.util.ddd.pattern.events.EventPublisher;
import org.home.blackjack.util.locking.FinegrainedLockable;

@Named
public class SerializingGameRepository implements GameRepository, FinegrainedLockable<GameID> {
	
	@Resource
	private EventPublisher eventPublisher;
	@Resource
	private final GameStore gameStore;
	@Resource
	private final PersistenceAssembler<Game, PersistenceObject<Game>> gameStoreAssembler;
	
	public SerializingGameRepository(GameStore gameStore) {
		this.gameStore = gameStore;
		this.gameStoreAssembler = gameStore.assembler();
	}
	
	@Override
	public Game find(GameID gameID) {
		PersistenceObject<Game> po = gameStore.find(gameStoreAssembler.toPersistence(gameID));
		if (po == null) {
			throw new GameNotFoundException(gameID);
		}
		Game game = gameStoreAssembler.toDomain(po);
		game.setEventPublisher(eventPublisher);
		return game;
	}

	@Override
	public void create(Game game) {
		PersistenceObject<Game> po = gameStoreAssembler.toPersistence(game);
		gameStore.create(po);
	}

	@Override
	public void update(Game game) {
		PersistenceObject<Game> po = gameStoreAssembler.toPersistence(game);
		gameStore.update(po);
	}
	
	@Override
	public Lock getLockForKey(GameID key) {
		return gameStore.getLockForKey(key);
	}

	
}
