package ru.nsu.ccfit.chirikhin.factory;

import ru.nsu.ccfit.chirikhin.blockingqueue.BlockingQueue;

import java.util.Observable;

public class Storage<T> extends Observable {

    final private int maxSize;

    BlockingQueue<T> items;

    public Storage(int maxSize) {
        this.maxSize = maxSize;
        items = new BlockingQueue<>(maxSize);
    }

    public T getNext() throws StorageEmptyException, InterruptedException {
        if (0 == items.size()) {
            throw new StorageEmptyException("Engine Storage is empty! Trying to get next engine!");
        }
        else {
            return items.pop();
        }
    }

    public void add(T item) throws StorageOverflowedException, InterruptedException {
        if (maxSize == items.size()) {
            throw new StorageOverflowedException("Storage of " + item.toString() + " is overflowed");
        }

        items.put(item);
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
