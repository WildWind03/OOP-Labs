package ru.nsu.ccfit.chirikhin.factory;

import org.apache.log4j.Logger;

public class StorageEventContext {
    private Logger logger = Logger.getLogger(StorageEventContext.class.getName());
    private final StorageEvent storageEvent;
    private final int currentFullness;

    public StorageEventContext(StorageEvent storageEvent, int currentFullness) {
        this.storageEvent = storageEvent;
        this.currentFullness = currentFullness;
    }

    public StorageEvent getStorageEvent() {
        return storageEvent;
    }

    public int getCurrentFullness() {
        return currentFullness;
    }
}
