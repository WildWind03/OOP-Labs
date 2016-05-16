package ru.nsu.ccfit.chirikhin.cyclequeue;

import org.apache.log4j.Logger;

import java.util.LinkedList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class CycleQueue<T> {
    private static final Logger logger = Logger.getLogger(CycleQueue.class.getName());

    private final BlockingQueue<T> innerQueue = new LinkedBlockingQueue<>();

    private final int maxSize;
    private int currentSize = 0;

    public CycleQueue(int maxSize) {
        this.maxSize = maxSize;
    }

    public synchronized void put(T item) throws InterruptedException {
        if (currentSize + 1 > maxSize) {
            innerQueue.take();
            currentSize--;
        }

        innerQueue.put(item);
        currentSize++;
    }

    public synchronized LinkedList<T> get() {
        return new LinkedList<T>(innerQueue);
    }
}
