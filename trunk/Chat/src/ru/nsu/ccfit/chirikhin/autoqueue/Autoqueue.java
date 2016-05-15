package ru.nsu.ccfit.chirikhin.autoqueue;

import org.apache.log4j.Logger;

import java.util.LinkedList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Autoqueue<T> {
    private static final Logger logger = Logger.getLogger(Autoqueue.class.getName());

    private final BlockingQueue<T> innerQueue = new LinkedBlockingQueue<>();

    private final int size;
    private AtomicInteger currentSize = new AtomicInteger(0);

    public Autoqueue(int size) {
        this.size = size;
    }

    public void put(T item) throws InterruptedException {
        if (currentSize.incrementAndGet() > size) {
            innerQueue.take();
        }

        innerQueue.put(item);
    }

    public LinkedList<T> get() {
        return new LinkedList<T>(innerQueue);
    }
}
