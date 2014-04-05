package org.home.blackjack.core.infrastructure.persistence.player;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.inject.Named;

import org.home.blackjack.core.domain.player.Player;
import org.home.blackjack.core.domain.player.PlayerNotFoundException;
import org.home.blackjack.core.domain.player.PlayerRepository;
import org.home.blackjack.core.domain.shared.EventBusManager;
import org.home.blackjack.core.domain.shared.PlayerID;
import org.home.blackjack.core.infrastructure.persistence.player.store.PlayerStore;
import org.home.blackjack.core.infrastructure.persistence.shared.PersistenceAssembler;
import org.home.blackjack.core.infrastructure.persistence.shared.PersistenceObject;
import org.home.blackjack.util.ddd.pattern.events.LightweightDomainEventBus;
import org.home.blackjack.util.marker.hexagonal.DrivingAdapter;

import com.google.common.collect.Lists;

@Named
public class SerializingPlayerRepository implements PlayerRepository, DrivingAdapter<PlayerRepository> {
	
	private final PlayerStore playerStore;
	private final PersistenceAssembler<Player, PersistenceObject<Player>> playerStoreAssembler;
	
	@Resource
    private EventBusManager eventBusManager;
	
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
		Player player = toDomain(po);
		return player;
	}

	//TODO should not sort here
    @Override
    public List<Player> findAllSortedByWinNumber() {
        List<Player> result = Lists.newArrayList();
        List<PersistenceObject<Player>> pos = playerStore.findAllSortedByWinNumber();
        for (PersistenceObject<Player> po : pos) {
            result.add(toDomain(po));
        }
        Comparator<Player> comparator = new Comparator<Player>() {
            @Override
            public int compare(Player p1, Player p2) {
                return Integer.valueOf(p1.getWinNumber()).compareTo(p2.getWinNumber());
            }
        };
        Collections.sort(result, comparator);
        return result;
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
	
    private Player toDomain(PersistenceObject<Player> po) {
        Player player = playerStoreAssembler.toDomain(po);
        player.setDomainEventPublisher(eventBusManager.domainEventPublisherInstance());
        return player;
    }
	
}
