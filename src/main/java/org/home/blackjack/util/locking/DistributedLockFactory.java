package org.home.blackjack.util.locking;

import java.util.concurrent.locks.Lock;

public interface DistributedLockFactory<K> {
    
    Lock createLockForKey(K key);
}
