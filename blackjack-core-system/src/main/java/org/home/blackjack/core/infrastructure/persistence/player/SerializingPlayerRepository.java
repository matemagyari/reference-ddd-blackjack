package org.home.blackjack.core.infrastructure.persistence.player;

import javax.inject.Inject;
import javax.inject.Named;

import org.home.blackjack.core.domain.player.Player;
import org.home.blackjack.core.domain.player.PlayerNotFoundException;
import org.home.blackjack.core.domain.player.PlayerRepository;
import org.home.blackjack.core.domain.shared.PlayerID;
import org.home.blackjack.core.infrastructure.persistence.player.store.PlayerStore;
import org.home.blackjack.core.infrastructure.persistence.shared.PersistenceAssembler;
import org.home.blackjack.core.infrastructure.persistence.shared.PersistenceObject;
import org.home.blackjack.util.ddd.pattern.events.LightweightDomainEventBus;
import org.home.blackjack.util.marker.hexagonal.DrivingAdapter;

@Named
public class SerializingPlayerRepository implements PlayerRepository, DrivingAdapter<PlayerRepository> {
	
	private final PlayerStore playerStore;
	private final PersistenceAssembler<Player, PersistenceObject<Player>> playerStoreAssembler;
	
	@Inject
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
		player.setDomainEventPublisher(LightweightDomainEventBus.domainEventPublisherInstance());
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

	@Override
	public void clear() {
		playerStore.clear();
	}

	
}
