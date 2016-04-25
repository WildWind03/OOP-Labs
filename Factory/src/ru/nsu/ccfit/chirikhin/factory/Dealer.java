package ru.nsu.ccfit.chirikhin.factory;


import org.apache.log4j.Logger;

public class Dealer implements Runnable{
    private Storage<Car> carStorage;
    private String name;

    Logger logger = Logger.getLogger(Dealer.class.getName());

    private int timeToSleep = 5000;

    private boolean isRunning;

    public void setSellingSpeed(int newSpeed) {
        this.timeToSleep = newSpeed;
    }

    public Dealer(Storage<Car> carStorage, String name) {
        if (null == carStorage || null == name) {
            throw new IllegalArgumentException("Null references in constuctor");
        }

        this.carStorage = carStorage;
        this.name = name;
        logger.info("Dealer with name " + name + " has been created!");
        isRunning = true;
    }

    @Override
    public void run() {
        Car car;
        try {
            while(isRunning) {
                car = carStorage.getNext();
                logger.info("Car with id " + car.getId() + " has been successfully sold by dealer with name " + name);
                Thread.sleep(timeToSleep);
            }
        } catch (InterruptedException e) {
            logger.error(name + ": unexpected interrupted error!");
        }
    }

    public void kill() {
        isRunning = false;
    }

}
