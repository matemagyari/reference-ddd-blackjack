package org.home.blackjack.domain;

import java.util.Collection;

/**
 * Interface defining common Repository behaviour, that is to say they should be map-like and only work with Aggregate
 * Roots.
 * 
 * @author michele.sollecito
 */
public interface Repository<K extends ID, V extends AggregateRoot<K>> {

	/**
	 * Returns the number of key-value mappings in this map. If the map contains more than <tt>Integer.MAX_VALUE</tt>
	 * elements, returns <tt>Integer.MAX_VALUE</tt>.
	 * 
	 * @return the number of key-value mappings in this map
	 */
	int size();

	/**
	 * Returns <tt>true</tt> if this map contains no key-value mappings.
	 * 
	 * @return <tt>true</tt> if this map contains no key-value mappings
	 */
	boolean isEmpty();

	/**
	 * Returns <tt>true</tt> if this map contains a mapping for the specified key. More formally, returns <tt>true</tt>
	 * if and only if this map contains a mapping for a key <tt>k</tt> such that
	 * <tt>(key==null ? k==null : key.equals(k))</tt>. (There can be at most one such mapping.)
	 * 
	 * @param key
	 *            key whose presence in this map is to be tested
	 * @return <tt>true</tt> if this map contains a mapping for the specified key
	 * @throws ClassCastException
	 *             if the key is of an inappropriate type for this map (<a
	 *             href="Collection.html#optional-restrictions">optional</a>)
	 * @throws NullPointerException
	 *             if the specified key is null and this map does not permit null keys (<a
	 *             href="Collection.html#optional-restrictions">optional</a>)
	 */
	boolean containsKey(K key);

	/**
	 * Returns <tt>true</tt> if this map maps one or more keys to the specified value. More formally, returns
	 * <tt>true</tt> if and only if this map contains at least one mapping to a value <tt>v</tt> such that
	 * <tt>(value==null ? v==null : value.equals(v))</tt>. This operation will probably require time linear in the map
	 * size for most implementations of the <tt>Map</tt> interface.
	 * 
	 * @param value
	 *            value whose presence in this map is to be tested
	 * @return <tt>true</tt> if this map maps one or more keys to the specified value
	 * @throws ClassCastException
	 *             if the value is of an inappropriate type for this map (<a
	 *             href="Collection.html#optional-restrictions">optional</a>)
	 * @throws NullPointerException
	 *             if the specified value is null and this map does not permit null values (<a
	 *             href="Collection.html#optional-restrictions">optional</a>)
	 */
	boolean containsValue(V value);

	/**
	 * Returns the value to which the specified key is mapped, or {@code null} if this map contains no mapping for the
	 * key.
	 * 
	 * <p>
	 * More formally, if this map contains a mapping from a key {@code k} to a value {@code v} such that
	 * {@code (key==null ? k==null :
	 * key.equals(k))}, then this method returns {@code v}; otherwise it returns {@code null}. (There can be at most one
	 * such mapping.)
	 * 
	 * <p>
	 * If this map permits null values, then a return value of {@code null} does not <i>necessarily</i> indicate that
	 * the map contains no mapping for the key; it's also possible that the map explicitly maps the key to {@code null}.
	 * The {@link #containsKey containsKey} operation may be used to distinguish these two cases.
	 * 
	 * @param key
	 *            the key whose associated value is to be returned
	 * @return the value to which the specified key is mapped, or {@code null} if this map contains no mapping for the
	 *         key
	 * @throws ClassCastException
	 *             if the key is of an inappropriate type for this map (<a
	 *             href="Collection.html#optional-restrictions">optional</a>)
	 * @throws NullPointerException
	 *             if the specified key is null and this map does not permit null keys (<a
	 *             href="Collection.html#optional-restrictions">optional</a>)
	 */
	V get(K key);

	/**
	 * Associates the specified value with the specified key in this map (optional operation). If the map previously
	 * contained a mapping for the key, the old value is replaced by the specified value. (A map <tt>m</tt> is said to
	 * contain a mapping for a key <tt>k</tt> if and only if {@link #containsKey(K) m.containsKey(k)} would return
	 * <tt>true</tt>.)
	 * 
	 * @param value
	 *            value to be associated with the specified key
	 * @return the previous value associated with <tt>key</tt>, or <tt>null</tt> if there was no mapping for
	 *         <tt>key</tt>. (A <tt>null</tt> return can also indicate that the map previously associated <tt>null</tt>
	 *         with <tt>key</tt>, if the implementation supports <tt>null</tt> values.)
	 * @throws UnsupportedOperationException
	 *             if the <tt>put</tt> operation is not supported by this map
	 * @throws ClassCastException
	 *             if the class of the specified key or value prevents it from being stored in this map
	 * @throws NullPointerException
	 *             if the specified key or value is null and this map does not permit null keys or values
	 * @throws IllegalArgumentException
	 *             if some property of the specified key or value prevents it from being stored in this map
	 */
	V put(V value);

	/**
	 * Removes the mapping for a key from this map if it is present (optional operation). More formally, if this map
	 * contains a mapping from key <tt>k</tt> to value <tt>v</tt> such that
	 * <code>(key==null ?  k==null : key.equals(k))</code>, that mapping is removed. (The map can contain at most one
	 * such mapping.)
	 * 
	 * <p>
	 * Returns the value to which this map previously associated the key, or <tt>null</tt> if the map contained no
	 * mapping for the key.
	 * 
	 * <p>
	 * If this map permits null values, then a return value of <tt>null</tt> does not <i>necessarily</i> indicate that
	 * the map contained no mapping for the key; it's also possible that the map explicitly mapped the key to
	 * <tt>null</tt>.
	 * 
	 * <p>
	 * The map will not contain a mapping for the specified key once the call returns.
	 * 
	 * @param key
	 *            key whose mapping is to be removed from the map
	 * @return the previous value associated with <tt>key</tt>, or <tt>null</tt> if there was no mapping for
	 *         <tt>key</tt>.
	 * @throws UnsupportedOperationException
	 *             if the <tt>remove</tt> operation is not supported by this map
	 * @throws ClassCastException
	 *             if the key is of an inappropriate type for this map (<a
	 *             href="Collection.html#optional-restrictions">optional</a>)
	 * @throws NullPointerException
	 *             if the specified key is null and this map does not permit null keys (<a
	 *             href="Collection.html#optional-restrictions">optional</a>)
	 */
	V remove(K key);

	/**
	 * Returns a {@link java.util.Collection} view of the values contained in this map. The collection is backed by the
	 * map, so changes to the map are reflected in the collection, and vice-versa. If the map is modified while an
	 * iteration over the collection is in progress (except through the iterator's own <tt>remove</tt> operation), the
	 * results of the iteration are undefined. The collection supports element removal, which removes the corresponding
	 * mapping from the map, via the <tt>Iterator.remove</tt>, <tt>Collection.remove</tt>, <tt>removeAll</tt>,
	 * <tt>retainAll</tt> and <tt>clear</tt> operations. It does not support the <tt>add</tt> or <tt>addAll</tt>
	 * operations.
	 * 
	 * @return a collection view of the values contained in this map
	 */
	Collection<V> values();
}
