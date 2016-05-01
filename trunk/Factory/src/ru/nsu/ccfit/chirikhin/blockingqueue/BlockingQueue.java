package ru.nsu.ccfit.chirikhin.blockingqueue;

import ru.nsu.ccfit.chirikhin.factory.StorageEvent;
import ru.nsu.ccfit.chirikhin.factory.StorageEventContext;

import java.util.LinkedList;
import java.util.Observable;

public class BlockingQueue<T> extends Observable {

    private final int maxSize;
    private final Object lock;
    private final LinkedList<T> insideQueue;

    public BlockingQueue(int maxSize) {
        if (maxSize <= 0) {
            throw new IllegalArgumentException("Invalid max size");
        }

        this.maxSize = maxSize;
        lock = new Object();
        insideQueue = new LinkedList<>();
    }

    public int maxSize() {
        return maxSize;
    }

    public int size() {
        synchronized (lock) {
            return insideQueue.size();
        }
    }

    public boolean isEmpty() {
        synchronized (lock) {
            return insideQueue.isEmpty();
        }
    }

    public boolean isFull() {
        synchronized (lock) {
            if (maxSize >= insideQueue.size()) {
                return true;
            }
        }

        return false;
    }

    public void put(T obj) throws InterruptedException {
        synchronized (lock) {
            while (insideQueue.size() >= maxSize) {
                lock.wait();
            }

            insideQueue.add(obj);
            setChanged();
            notifyObservers(new StorageEventContext(StorageEvent.PUT, insideQueue.size(), maxSize()));
            lock.notifyAll();
        }
    }

    public T pop() throws InterruptedException {
        synchronized (lock) {
            while (insideQueue.isEmpty()) {
               lock.wait();
            }

            T current = insideQueue.pop();
            setChanged();
            notifyObservers(new StorageEventContext(StorageEvent.GET, insideQueue.size(), maxSize()));
            lock.notifyAll();
            return current;

        }
    }
}
