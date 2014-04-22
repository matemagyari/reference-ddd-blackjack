package org.home.blackjack.core.domain.table;

import java.util.List;

import org.home.blackjack.core.domain.shared.PlayerID;
import org.home.blackjack.core.domain.shared.TableID;
import org.home.blackjack.core.domain.table.event.PlayerIsSeatedEvent;
import org.home.blackjack.core.domain.table.event.TableEvent;
import org.home.blackjack.core.domain.table.event.TableIsFullEvent;
import org.home.blackjack.core.domain.table.event.TableSeatingChangedEvent;
import org.home.blackjack.util.ddd.pattern.domain.exception.DomainException;
import org.home.blackjack.util.ddd.pattern.domain.model.AggregateRoot;

import com.google.common.collect.Lists;

public class Table extends AggregateRoot<TableID> {

	// currently we only use tables of size 2
	private final int size = 2;
	private final List<PlayerID> players = Lists.newArrayList();

	public Table(TableID tableID) {
		super(tableID);
	}

	public void playerLeaves(PlayerID player) {
	    players.remove(player);
	    publish(new TableSeatingChangedEvent(getID(), players));
	}
	
	
	public boolean playerSits(PlayerID player) {
		if (players.contains(player)) {
			throw new DomainException(player + " already sits by this table");
		} else if (isFull()) {
			return false;
		} else {
			players.add(player);
			publish(new PlayerIsSeatedEvent(getID(), player));
			publish(new TableSeatingChangedEvent(getID(), players));
			if (isFull()) {
				publish(new TableIsFullEvent(getID(), players));
			}
			return true;
		}
	}

	public void clearTable() {
		players.clear();
		publish(new TableSeatingChangedEvent(getID(), players));
	}

	private void publish(TableEvent event) {
		domainEventPublisher().publish(event);
	}

	public boolean isFull() {
		return players.size() == size;
	}
	
	public List<PlayerID> getPlayers() {
		return Lists.newArrayList(players) ;
	}

	@Override
	public String toString() {
		return "Table [size=" + size + ", players=" + players + ", getID()=" + getID() + "]";
	}
	
}
