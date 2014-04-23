package org.home.blackjack.core.infrastructure.adapters.driving.persistence.player.repository;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.annotation.Resource;
import javax.inject.Inject;

import org.home.blackjack.core.domain.player.Player;
import org.home.blackjack.core.domain.player.PlayerNotFoundException;
import org.home.blackjack.core.domain.player.PlayerRepository;
import org.home.blackjack.core.domain.shared.PlayerID;
import org.home.blackjack.core.infrastructure.adapters.driving.persistence.player.store.PlayerStore;
import org.home.blackjack.util.ddd.pattern.domain.events.DomainEventPublisherFactory;
import org.home.blackjack.util.ddd.pattern.infrastructure.persistence.PersistenceAssembler;
import org.home.blackjack.util.ddd.pattern.infrastructure.persistence.PersistenceObject;
import org.home.blackjack.util.marker.hexagonal.DrivingAdapter;

import com.google.common.collect.Lists;

public class SerializingPlayerRepository implements PlayerRepository, DrivingAdapter<PlayerRepository> {
	
	private final PlayerStore playerStore;
	private final PersistenceAssembler<Player, PersistenceObject<Player>> playerStoreAssembler;
	
	@Resource
    private DomainEventPublisherFactory domainEventPublisherFactory;
	
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
        player.setDomainEventPublisher(domainEventPublisherFactory.domainEventPublisherInstance());
        return player;
    }
	
}
