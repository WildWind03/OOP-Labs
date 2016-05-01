package ru.nsu.ccfit.chirikhin.factory;


import org.apache.log4j.Logger;

public class Dealer implements Runnable{
    private final Storage<Car> carStorage;
    private final String name;

    private final static Logger logger = Logger.getLogger(Dealer.class.getName());

    private int timeToSleep = 5000;

    public void setSellingSpeed(int newSpeed) {
        if (newSpeed < 0) {
            throw new IllegalArgumentException("Speed ca not be negative");
        }

        this.timeToSleep = newSpeed;
    }

    public Dealer(Storage<Car> carStorage, String name, int sellingSpeed) {
        if (null == carStorage || null == name) {
            throw new IllegalArgumentException("Null references in constuctor");
        }

        if (sellingSpeed < 0) {
            throw new IllegalArgumentException("Selling speed can't be negative!");
        }

        timeToSleep = sellingSpeed;
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
                logger.info("Car with id " + car.getId() + "(Engine ID: " + car.getEngineId() + " Car Body id: " + car.getBodyId() + " Accessory id: " + car.getAccessoryId() + ") has been successfully sold by dealer with name " + name);
                Thread.sleep(timeToSleep);
            }
        } catch (InterruptedException e) {
            logger.error(name + " :interrupting");
        }

        logger.info("Dealer has finished successfully!");
    }

}
