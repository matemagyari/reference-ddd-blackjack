package org.home.blackjack.util.locking;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;

import org.apache.log4j.Logger;


public abstract class AbstractStoreLock<S> implements Lock {

    private static final Logger log = Logger.getLogger(AbstractStoreLock.class);
    protected final int maxLockTries = 20;
    protected final S key;
    protected final long minDelayMilis = 4;
    protected final long maxDelayMilis = 2000;
    private ThreadLocal<Random> rand;

    public AbstractStoreLock(S key) {
        this.key = key;
        rand = new ThreadLocal<Random>();
        // every thread has its own random number generator
        rand.set(new Random());

    }

    @Override
    public void lock() {
        int lockCount = 0;
        //thread that creates the lock may be different than the one that calls 'lock'
        rand.set(new Random());
        while (!tryLock()) {
            if (++lockCount > maxLockTries) {
                handleUnableToAcquireLock();
            }
            long delay = calculateDelay(lockCount);
            log.debug("lock not acquired for "+key+", sleeping "+delay+" ms");
            LockSupport.parkUntil(System.currentTimeMillis()+delay);
        }
    }

    /**
     * Override to provide custom error handling
     */
    protected void handleUnableToAcquireLock() {
        throw new RuntimeException("Unable to acquire lock after " + maxLockTries + " tries. Key=" + key);
    }

    protected long calculateDelay(int lockCount) {
        long backoffSeed = Math.round(Math.pow(lockCount, 2));
        if (backoffSeed > maxDelayMilis){
            return maxDelayMilis;
        }
        backoffSeed = Math.min(Integer.MAX_VALUE, backoffSeed);
        Random r = rand.get();
        long result = minDelayMilis + r.nextInt((int)backoffSeed);
        return Math.min(result, maxDelayMilis);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        while (!tryLock()) {
            Thread.sleep(maxDelayMilis);
        }
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        long start = System.currentTimeMillis();
        long end = start + TimeUnit.MILLISECONDS.convert(time, unit);

        boolean acquired = false;
        boolean timeout = false;

        while (!(acquired || timeout)) {
            acquired = tryLock();
            timeout = System.currentTimeMillis() >= end;
        }

        return acquired;
    }

    @Override
    public Condition newCondition() {
        throw new UnsupportedOperationException();
    }

    protected S getKey() {
        return key;
    }
}
