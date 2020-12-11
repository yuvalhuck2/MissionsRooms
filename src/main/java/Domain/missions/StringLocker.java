package Domain.missions;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class StringLocker {
    private ReentrantReadWriteLock lock;
    private AtomicInteger numberLocks;

    public StringLocker() {
        this.lock = new ReentrantReadWriteLock();
        this.numberLocks = new AtomicInteger(1);
        this.lock.writeLock().lock();
    }


    public int increaseLocker(){
        this.lock.writeLock().lock();
        return numberLocks.incrementAndGet();
    }

    public int decreaseLocker(){
        lock.writeLock().unlock();
        return numberLocks.decrementAndGet();

    }


    public void writeLock() {
        lock.writeLock().lock();
    }

    public void writeRelease() {
        lock.writeLock().unlock();
    }
}
