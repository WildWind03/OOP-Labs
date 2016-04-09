package ru.nsu.ccfit.chirikhin.factory;

import org.apache.log4j.Logger;

public class CarStorageController implements Runnable{
    Storage<Car> carStorage;
    CarCollectors carCollectors;
    Logger logger = Logger.getLogger(CarStorageController.class.getName());

    public CarStorageController(Storage<Car> carStorage, CarCollectors carCollectors) {
        this.carStorage = carStorage;
        this.carCollectors = carCollectors;
    }

    @Override
    public void run() {
        while(!carStorage.isFull()) {
            try {
                carCollectors.makeCar();
            } catch (InterruptedException e) {
                logger.error(e.toString());
            }
        }
    }
}
