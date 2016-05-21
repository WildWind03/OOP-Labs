package ru.nsu.ccfit.chirikhin.waitingblockingqueue;

import org.apache.log4j.Logger;
import ru.nsu.ccfit.chirikhin.chat.TimeoutException;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class WaitingBlockingQueue <T>{
    private static final Logger logger = Logger.getLogger(WaitingBlockingQueue.class.getName());
    private final int TIMEOUT = 3000;

    private final BlockingQueue<T> myQueue = new LinkedBlockingQueue<>();
    private final Object lock = new Object();
    private boolean isNotified;

    public void push(T t) throws InterruptedException, TimeoutException {
        synchronized (lock) {
            isNotified = false;
            myQueue.put(t);
            lock.wait(TIMEOUT);
            if (!isNotified) {
                throw new TimeoutException("The myQueue has no been notified");
            }
        }
    }

    public void notifyQueue() {
        synchronized (lock) {
            isNotified = true;
            lock.notifyAll();
        }
    }

    public T pop() {
        synchronized (lock) {
            T t = myQueue.poll();
            return t;
        }
    }


}
