package org.home.blackjack.util.locking;

import java.util.concurrent.locks.Lock;

import org.apache.log4j.Logger;

public class LockTemplate {

    private static final Logger LOG = Logger.getLogger(LockTemplate.class);

    public <K, T> T doWithLock(FinegrainedLockable<K> lockable, K key, WriteLockingAction<K, T> action) {
        Lock lock = lockable.getLockForKey(key);
        // lock.lock() throws RuntimeException if didn't manage to acquire a lock
        lock.lock();
        try {
            log(String.format("locked on %s using key %s", lockable, key));
            return action.withWriteLock(key);
        } finally {
            lock.unlock();
            log(String.format("unlocked on %s using key %s", lockable, key));
        }
    }

    public <K> void doWithLock(FinegrainedLockable<K> lockable, K key, final VoidWriteLockingAction<K> action) {
        doWithLock(lockable, key, new WriteLockingAction<K, Object>() {
            public Object withWriteLock(K key) {
                action.withWriteLock(key);
                return null;
            }
        });
    }
    
    private static void log(String logMessage) {
        if(LOG.isDebugEnabled()) {
            LOG.debug(logMessage);
        }
    }
}
