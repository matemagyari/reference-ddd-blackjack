package org.home.blackjack.infrastructure.persistence;

import java.util.Map;

import javax.inject.Named;

import org.home.blackjack.domain.table.Table;
import org.home.blackjack.domain.table.TableRepository;
import org.home.blackjack.domain.table.core.TableID;
import org.home.blackjack.util.ddd.pattern.events.LightweightDomainEventBus;

import com.google.common.collect.Maps;

@Named
public class InMemoryTableRepository implements TableRepository {
	
	private final Map<TableID, Table> map = Maps.newHashMap();

	@Override
	public Table find(TableID tableID) {
		Table table = map.get(tableID);
		table.setDomainEventPublisher(LightweightDomainEventBus.domainEventPublisherInstance());
		return table;
	}

	@Override
	public void update(Table table) {
		map.put(table.getID(), table);
	}

	@Override
	public void create(Table table) {
		map.put(table.getID(), table);
	}

	@Override
	public void clear() {
		map.clear();
	}

}
