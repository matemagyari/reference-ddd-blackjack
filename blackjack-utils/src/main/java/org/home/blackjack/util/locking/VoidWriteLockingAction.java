package org.home.blackjack.util.locking;

public interface VoidWriteLockingAction<K> {
    void withWriteLock(K key);
}
