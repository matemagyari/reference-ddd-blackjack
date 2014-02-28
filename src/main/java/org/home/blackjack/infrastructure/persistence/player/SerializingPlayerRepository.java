package org.home.blackjack.infrastructure.persistence.player;

import javax.annotation.Resource;
import javax.inject.Named;

import org.home.blackjack.domain.player.Player;
import org.home.blackjack.domain.player.PlayerNotFoundException;
import org.home.blackjack.domain.player.PlayerRepository;
import org.home.blackjack.domain.shared.PlayerID;
import org.home.blackjack.infrastructure.persistence.player.store.PlayerStore;
import org.home.blackjack.infrastructure.persistence.shared.PersistenceAssembler;
import org.home.blackjack.infrastructure.persistence.shared.PersistenceObject;
import org.home.blackjack.util.ddd.pattern.events.EventPublisher;

@Named
public class SerializingPlayerRepository implements PlayerRepository {
	
	@Resource
	private EventPublisher eventPublisher;
	private final PlayerStore playerStore;
	private final PersistenceAssembler<Player, PersistenceObject<Player>> playerStoreAssembler;
	
	public SerializingPlayerRepository(PlayerStore playerStore) {
		this.playerStore = playerStore;
		this.playerStoreAssembler = playerStore.assembler();
	}
	
	@Override
	public Player find(PlayerID playerID) {
		PersistenceObject<Player> po = playerStore.find(playerStoreAssembler.toPersistence(playerID));
		if (po == null) {
			throw new PlayerNotFoundException(playerID);
		}
		Player player = playerStoreAssembler.toDomain(po);
		player.setEventPublisher(eventPublisher);
		return player;
	}

	@Override
	public void update(Player player) {
		PersistenceObject<Player> po = playerStoreAssembler.toPersistence(player);
		playerStore.update(po);
	}

	@Override
	public void create(Player player) {
		PersistenceObject<Player> po = playerStoreAssembler.toPersistence(player);
		playerStore.create(po);
	}

	
}
