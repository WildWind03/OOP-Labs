package ru.nsu.ccfit.chirikhin.factory;

import ru.nsu.ccfit.chirikhin.blockingqueue.BlockingQueue;


public class AccessoryStorage {

    private BlockingQueue<Accessory> accessories;

    public AccessoryStorage(int maxSize) {
        if (maxSize <= 0) {
            throw new IllegalArgumentException("AccessoryStorage: illegal size of the storage!");
        }
        accessories = new BlockingQueue<>(maxSize);
    }

    public Accessory getNextDetail() throws StorageEmptyException, InterruptedException {
        if (isEmpty()) {
            throw new StorageEmptyException("Engine Storage is empty! Trying to get next engine!");
        }
        else {
            return accessories.pop();
        }
    }

    public void addDetail(Accessory accessory) throws StorageOverflowedException, InterruptedException {
        if (isFull()) {
            throw new StorageOverflowedException("Storage of " + accessory.toString() + " is overflowed");
        }

        accessories.put(accessory);
    }

    public boolean isEmpty() {
        return accessories.isEmpty();
    }

    public boolean isFull() {
        return accessories.isFull();
    }

    public int getMaxSize() {
        return accessories.maxSize();
    }


}
