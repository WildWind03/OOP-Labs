package ru.nsu.ccfit.chirikhin.factory;

import org.apache.log4j.Logger;

import java.util.Observable;
import java.util.Observer;

public class CarStorageController implements Runnable, Observer {
    private Storage<Car> carStorage;
    private CarCollectors carCollectors;
    private Logger logger = Logger.getLogger(CarStorageController.class.getName());

    private final Object lock;

    private boolean isRunning;

    public CarStorageController(Storage<Car> carStorage, CarCollectors carCollectors) {
        if (null == carCollectors || null == carStorage) {
            throw new IllegalArgumentException("Null refrence in constructor");
        }

        carStorage.addObserver(this);
        this.carStorage = carStorage;
        this.carCollectors = carCollectors;
        lock = new Object();
        isRunning = true;
    }

    @Override
    public void run() {
        while(isRunning) {
            try {
                synchronized (lock) {
                    if (carStorage.size() < carStorage.getMaxSize() / 2) {
                        carCollectors.makeCars(carStorage.getMaxSize() / 2);
                    }
                    lock.wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg == StorageEvent.PUT) {
            synchronized (lock) {
                lock.notifyAll();
            }
        }
    }

    public void kill() {
        synchronized (lock) {
            isRunning = false;
            lock.notifyAll();
        }
    }
}
