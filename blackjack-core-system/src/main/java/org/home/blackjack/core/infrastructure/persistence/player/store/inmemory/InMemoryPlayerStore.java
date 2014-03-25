package org.home.blackjack.core.infrastructure.persistence.player.store.inmemory;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.inject.Inject;
import javax.inject.Named;

import org.home.blackjack.core.domain.player.Player;
import org.home.blackjack.core.domain.shared.PlayerID;
import org.home.blackjack.core.infrastructure.persistence.player.store.PlayerStore;
import org.home.blackjack.core.infrastructure.persistence.shared.PersistenceObject;
import org.home.blackjack.core.infrastructure.persistence.shared.PersistenceObjectId;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

@Named
public class InMemoryPlayerStore implements PlayerStore {
	
	private final Map<InMemoryPersistencePlayerId, String> jsonMap = Maps.newHashMap();

	private final InMemoryPlayerPersistenceAssembler playerStoreAssembler;
	
	@Inject
	public InMemoryPlayerStore(InMemoryPlayerPersistenceAssembler playerStoreAssembler) {
		this.playerStoreAssembler = playerStoreAssembler;
	}
	
	@Override
	public InMemoryPlayerPersistenceAssembler assembler() {
		return playerStoreAssembler;
	}

	@Override
	public InMemoryPersistencePlayer find(PersistenceObjectId<PlayerID> id) {
		InMemoryPersistencePlayerId playerID = (InMemoryPersistencePlayerId) id;
		String json = jsonMap.get(playerID);
		return new InMemoryPersistencePlayer(playerID, json);
	}
	
	//TODO it's not sorted here
    @Override
    public List<PersistenceObject<Player>> findAllSortedByWinNumber() {
        List<PersistenceObject<Player>> result = Lists.newArrayList();
        for(Entry<InMemoryPersistencePlayerId, String> entry : jsonMap.entrySet()) {
            result.add(new InMemoryPersistencePlayer(entry.getKey(), entry.getValue()));
        }
        return result;
    }

	@Override
	public void update(PersistenceObject<Player> po) {
		InMemoryPersistencePlayer mpg = (InMemoryPersistencePlayer) po;
		jsonMap.put(mpg.id(), mpg.getJson());
	}

	@Override
	public void create(PersistenceObject<Player> po) {
		InMemoryPersistencePlayer mpg = (InMemoryPersistencePlayer) po;
		jsonMap.put(mpg.id(), mpg.getJson());
	}
	
	@Override
	public void clear() {
		jsonMap.clear();
	}


}
