package ru.nsu.ccfit.chirikhin.factory;

import java.util.ArrayList;

public class CarDetailStorage <Detail> implements Storage {
    static final int defaultDetailIndexToGet = 0;

    final int maxSize;

    ArrayList<Detail> details;

    public CarDetailStorage(int maxSize) {
        this.maxSize = maxSize;
        details = new ArrayList<>();
    }

    public Detail getNextDetail() throws StorageEmptyException{
        if (0 == details.size()) {
            throw new StorageEmptyException("Engine Storage is empty! Trying to get next engine!");
        }
        else {
            return details.remove(defaultDetailIndexToGet);
        }
    }

    public void addDetail(Detail detail) throws StorageOverflowedException {
        if (maxSize == details.size()) {
            throw new StorageOverflowedException("Storage of " + detail.toString() + " is overflowed");
        }

        details.add(detail);
    }

    public boolean isEmpty() {
        if (0 == details.size()) {
            return true;
        }
        return false;
    }

    public boolean isFull() {
        if (maxSize == details.size()) {
            return true;
        }
        else {
            return false;
        }
    }

    public int getMaxSize() {
        return maxSize;
    }
}
