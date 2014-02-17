package org.home.blackjack.infrastructure.persistence;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.home.blackjack.domain.AggregateRoot;
import org.home.blackjack.domain.ID;
import org.home.blackjack.domain.Repository;

/**
 * Generic in-memory implementation of the Repository interface.
 * 
 * @param <K>
 *            type of the keys
 * @param <V>
 *            type of the values
 * 
 * @author michele.sollecito
 */
public abstract class InMemoryGenericRepository<K extends ID, V extends AggregateRoot<K>> implements Repository<K, V> {

	private Map<K, V> map;

	public InMemoryGenericRepository() {

		this.map = new ConcurrentHashMap<K, V>();
	}

	@Override
	public int size() {

		return map.size();
	}

	@Override
	public boolean isEmpty() {

		return map.isEmpty();
	}

	@Override
	public boolean containsKey(final K key) {

		return map.containsKey(key);
	}

	@Override
	public boolean containsValue(final V value) {

		return map.containsValue(value);
	}

	@Override
	public V get(final K key) {

		return map.get(key);
	}

	@Override
	public V put(final V value) {

		return map.put(value.getID(), value);
	}

	@Override
	public V remove(final K key) {

		return map.remove(key);
	}

	@Override
	public Collection<V> values() {

		return map.values();
	}
}
