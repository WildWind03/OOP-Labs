package ru.nsu.ccfit.chirikhin.factory;

import org.apache.log4j.Logger;
import ru.nsu.ccfit.chirikhin.blockingqueue.BlockingQueue;

import java.util.Observable;
import java.util.Observer;

public class Storage<T> extends Observable implements Observer{

    private final static Logger logger = Logger.getLogger(Storage.class.getName());

    private final BlockingQueue<T> items;

    public Storage(int maxSize) {
        if (maxSize <= 0) {
            throw new IllegalArgumentException("Size can't be negative!");
        }

        items = new BlockingQueue<>(maxSize);
        items.addObserver(this);
    }

    public T getNext() throws InterruptedException {
        T tmp = items.pop();
        return tmp;
    }

    public void add(T item) throws InterruptedException {
        items.put(item);
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public boolean isFull() {
        return items.isFull();
    }

    public int getMaxSize() {
        return items.maxSize();
    }

    public int size() {return items.size(); }

    @Override
    public void update(Observable o, Object arg) {
        setChanged();
        notifyObservers(arg);
    }
}
