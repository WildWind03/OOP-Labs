package ru.nsu.ccfit.chirikhin.factory;

public interface MyRunnable {
    int run () throws InterruptedException, StorageEmptyException, StorageOverflowedException;
}
