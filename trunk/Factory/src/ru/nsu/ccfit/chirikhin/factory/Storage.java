package ru.nsu.ccfit.chirikhin.factory;

import org.apache.log4j.Logger;
import ru.nsu.ccfit.chirikhin.blockingqueue.BlockingQueue;

import java.util.Observable;

public class Storage<T> extends Observable {

    Logger logger = Logger.getLogger(Storage.class.getName());

    public static class StorageContext {
        private int size;
        private int maxSize;

        public StorageContext(int size, int maxSize) {
            this.size = size;
            this.maxSize = maxSize;
        }

        public int getSize() {
            return size;
        }

        public int getMaxSize() {
            return maxSize;
        }
    }

    final private int maxSize;

    BlockingQueue<T> items;

    public Storage(int maxSize) {
        this.maxSize = maxSize;
        items = new BlockingQueue<>(maxSize);
    }

    public T getNext() throws StorageEmptyException, InterruptedException {
        T tmp = items.pop();
        setChanged();
        notifyObservers(new StorageContext(size(), getMaxSize()));
        return tmp;
    }

    public void add(T item) throws StorageOverflowedException, InterruptedException {
        items.put(item);
        logger.debug("size is " + items.size());
        setChanged();
        notifyObservers(new StorageContext(size(), getMaxSize()));
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
