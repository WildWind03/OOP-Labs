package ru.nsu.ccfit.chirikhin.blockingqueue;

import java.util.LinkedList;

public class BlockingQueue<T> {

    private int maxSize = 0;
    final private Object lock;
    private LinkedList<T> insideQueue;

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
        return insideQueue.size();
    }

    public boolean isEmpty() {
       return insideQueue.isEmpty();
    }

    public boolean isFull() {
        if (maxSize == insideQueue.size()) {
            return true;
        }

        return false;
    }

    public void put(T obj) throws InterruptedException {
        synchronized (lock) {
            while (insideQueue.size() == maxSize) {
                lock.wait();
            }

            lock.notify();

            insideQueue.add(obj);
        }
    }

    public T pop() throws InterruptedException {
        synchronized (lock) {
            while (insideQueue.isEmpty()) {
               lock.wait();
            }

            lock.notify();

            return insideQueue.pop();

        }
    }
}
