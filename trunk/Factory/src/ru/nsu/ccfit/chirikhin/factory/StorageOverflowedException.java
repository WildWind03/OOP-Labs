package ru.nsu.ccfit.chirikhin.factory;

public class StorageOverflowedException extends FactoryException {
    public StorageOverflowedException(String str) {
        super(str);
    }
}
