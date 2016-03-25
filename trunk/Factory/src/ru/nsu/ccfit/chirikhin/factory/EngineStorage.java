package ru.nsu.ccfit.chirikhin.factory;

import java.util.ArrayList;

/**
 * Created by cas on 21.03.16.
 */
public class EngineStorage implements Storage {
    static final int defaultEngineIndexToGet = 0;

    final int maxSize;

    int curSize;
    ArrayList<Engine> engines;

    public EngineStorage(int maxSize) {
        this.maxSize = maxSize;
    }

    public Engine getNextEngine() throws StorageEmptyException{
        if (0 == engines.size()) {
            throw new StorageEmptyException("Engine Storage is empty! Trying to get next engine!");
        }
        else {
            return engines.remove(defaultEngineIndexToGet);
        }
    }
}
