package ru.nsu.ccfit.chirikhin.factory;

import org.apache.log4j.Logger;

import java.util.Observable;
import java.util.Observer;

public class CarStorageController implements Runnable, Observer {
    private Storage<Car> carStorage;
    private CarCollectors carCollectors;
    private Logger logger = Logger.getLogger(CarStorageController.class.getName());

    private final Object lock;

    public CarStorageController(Storage<Car> carStorage, CarCollectors carCollectors) {
        if (null == carCollectors || null == carStorage) {
            throw new IllegalArgumentException("Null refrence in constructor");
        }

        carStorage.addObserver(this);
        this.carStorage = carStorage;
        this.carCollectors = carCollectors;
        lock = new Object();
    }

    @Override
    public void run() {
        try {
            while (true) {
                synchronized (lock) {

                    /*logger.debug("Checking car storage size!");
                    System.out.println(currentSize + "/" + (carStorage.getMaxSize() / 2));
                    if (currentSize < carStorage.getMaxSize() / 2) {
                        logger.debug("I want to make " + carStorage.getMaxSize() / 2 + " cars");
                        carCollectors.makeCars(carStorage.getMaxSize() / 2);
                    }
                    lock.wait();
                    */

                    carCollectors.makeCars(1);
                    lock.wait();

                }
            }
        } catch (InterruptedException e) {
            logger.error("Interrupted Exception!");
        }

        logger.info("Car Storage Controller finished successfully!");
    }

    @Override
    public void update(Observable o, Object arg) {
        synchronized (lock) {
            lock.notifyAll();
       }
    }
}
