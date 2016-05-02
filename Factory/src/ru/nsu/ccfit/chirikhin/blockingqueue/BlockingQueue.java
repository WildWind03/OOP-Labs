package ru.nsu.ccfit.chirikhin.blockingqueue;

import java.util.LinkedList;
import java.util.Observable;

public class BlockingQueue<T> extends Observable {

    public enum QueueEvent {
        PUT, POP
    }

    public static class QueueEventContext {
        private final int maxSize;
        private final int size;
        private final QueueEvent queueEvent;

        public QueueEventContext(QueueEvent queueEvent, int size, int maxSize) {
            if (null == queueEvent) {
                throw new NullPointerException("Null reference");
            }

            if (size > maxSize || size < 0 || maxSize <= 0) {
                throw new IllegalArgumentException("Invalid sizes");
            }

            this.maxSize = maxSize;
            this.size = size;
            this.queueEvent = queueEvent;
        }

        public int getMaxSize() {
            return maxSize;
        }

        public int getSize() {
            return size;
        }

        public QueueEvent getQueueEvent() {
            return queueEvent;
        }
    }

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
            notifyObservers(new QueueEventContext(QueueEvent.PUT, size(), maxSize()));
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
            notifyObservers(new QueueEventContext(QueueEvent.POP, size(), maxSize()));
            lock.notifyAll();
            return current;

        }
    }
}
