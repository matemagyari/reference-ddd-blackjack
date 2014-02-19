package org.home.blackjack.util.locking;

public interface WriteLockingAction<K, T> {

    T withWriteLock(K key);

}