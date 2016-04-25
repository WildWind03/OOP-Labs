package ru.nsu.ccfit.chirikhin.factory;

import org.apache.log4j.Logger;
import ru.nsu.ccfit.chirikhin.blockingqueue.BlockingQueue;

import java.util.Observable;

public class Storage<T> extends Observable {

    Logger logger = Logger.getLogger(Storage.class.getName());

    final private int maxSize;

    BlockingQueue<T> items;

    public Storage(int maxSize) {
        this.maxSize = maxSize;
        items = new BlockingQueue<>(maxSize);
    }

    public T getNext() throws InterruptedException {
        T tmp = items.pop();
        setChanged();
        notifyObservers(StorageEvent.GET);
        return tmp;
    }

    public void add(T item) throws InterruptedException {
        items.put(item);
        setChanged();
        notifyObservers(StorageEvent.PUT);
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public boolean isFull() {
        return items.isFull();
    }

    public int getMaxSize() {
        return maxSize;
    }

    public int size() {return items.size(); }
}
