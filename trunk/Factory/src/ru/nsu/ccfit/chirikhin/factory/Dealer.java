package ru.nsu.ccfit.chirikhin.factory;


import org.apache.log4j.Logger;

public class Dealer implements Runnable{
    Storage<Car> carStorage;
    private String name;

    Logger logger = Logger.getLogger(Dealer.class.getName());

    final static long timeToSleep = 5000;

    public Dealer(Storage<Car> carStorage, String name) {
        this.carStorage = carStorage;
        this.name = name;
        logger.info("Dealer with name " + name + " has been created!");
    }

    @Override
    public void run() {
        Car car;

        try {
            while(true) {
            car = carStorage.getNext();
            logger.info("Car with id " + car.getId() + " has been successfully sold by dealer with name " + name);
            Thread.sleep(timeToSleep);
            }
        } catch (StorageEmptyException e) {
            logger.error(name + ": can not get car from storage!");
        } catch (InterruptedException e) {
            logger.error(name + ": unexpected interrupted error!");
        }
    }
}
