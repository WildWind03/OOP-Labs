package ru.nsu.ccfit.chirikhin.factory;

import org.apache.log4j.Logger;

import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.atomic.AtomicBoolean;

public class CarStorageController implements Runnable, Observer {
    Storage<Car> carStorage;
    CarCollectors carCollectors;
    Logger logger = Logger.getLogger(CarStorageController.class.getName());

    private final Object lock;

    private boolean isRunning;

    private AtomicBoolean isInterrupted;

    public CarStorageController(Storage<Car> carStorage, CarCollectors carCollectors) {
        carStorage.addObserver(this);
        this.carStorage = carStorage;
        this.carCollectors = carCollectors;
        isInterrupted = new AtomicBoolean(false);
        lock = new Object();
        isRunning = true;
    }

    @Override
    public void run() {
        while(!isInterrupted.get() && isRunning) {
            try {
                synchronized (lock) {
                    carCollectors.makeCar();
                    isInterrupted.set(true);
                    lock.wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        if ((StorageEvent) arg == StorageEvent.PUT) {
            synchronized (lock) {
                isInterrupted.set(false);
                lock.notifyAll();
            }
        }
    }

    public void kill() {
        lock.notifyAll();
        isRunning = false;
    }
}
