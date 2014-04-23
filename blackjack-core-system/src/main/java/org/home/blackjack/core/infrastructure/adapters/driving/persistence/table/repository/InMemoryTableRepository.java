package org.home.blackjack.core.infrastructure.adapters.driving.persistence.table.repository;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.annotation.Resource;

import org.home.blackjack.core.domain.shared.TableID;
import org.home.blackjack.core.domain.table.Table;
import org.home.blackjack.core.domain.table.TableRepository;
import org.home.blackjack.util.ddd.pattern.domain.events.DomainEventPublisherFactory;
import org.home.blackjack.util.locking.FinegrainedLockable;
import org.home.blackjack.util.marker.hexagonal.DrivingAdapter;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class InMemoryTableRepository implements TableRepository, FinegrainedLockable<TableID>,  DrivingAdapter<TableRepository> {
	
	private final Map<TableID, Table> map = Maps.newHashMap();
	private final ConcurrentMap<TableID, Lock> locks = Maps.newConcurrentMap();
	
	@Resource
    private DomainEventPublisherFactory domainEventPublisherFactory;

	@Override
	public Table find(TableID tableID) {
		Table table = map.get(tableID);
		table.setDomainEventPublisher(domainEventPublisherFactory.domainEventPublisherInstance());
		return table;
	}
	
	@Override
	public List<Table> findAll() {
		return Lists.newArrayList(map.values());
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
		locks.clear();
	}
	
	@Override
	public boolean isEmpty() {
		return map.isEmpty();
	}

	@Override
	public Lock getLockForKey(TableID key) {
        locks.putIfAbsent(key, new ReentrantLock());
        return locks.get(key);
	}

}
