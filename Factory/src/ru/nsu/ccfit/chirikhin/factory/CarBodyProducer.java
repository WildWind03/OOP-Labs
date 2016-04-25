package ru.nsu.ccfit.chirikhin.factory;

import org.apache.log4j.Logger;

public class CarBodyProducer implements Runnable {

    private final Storage<CarBody> carBodyCarDetailStorage;
    private final Logger logger =  Logger.getLogger(AccessoryProducer.class.getName());
    private final IDRegisterer idRegisterer;
    private int producingSpeed;

    public void changeProducingSpeed(int producingSpeed) {
        if (producingSpeed < 0) {
            throw new IllegalArgumentException("Producing speed can't be negative");
        }
        this.producingSpeed = producingSpeed;
    }

    public CarBodyProducer(Storage<CarBody> carBodyCarDetailStorage, int producingSpeed, IDRegisterer idRegisterer) {

        if (null == carBodyCarDetailStorage || null == idRegisterer) {
            throw new NullPointerException("Can not create myself because of null reference!");
        }

        if (producingSpeed < 0) {
            throw new IllegalArgumentException("Producing speed is negative");
        }

        this.idRegisterer = idRegisterer;
        this.carBodyCarDetailStorage = carBodyCarDetailStorage;
        this.producingSpeed = producingSpeed;
    }

    @Override
    public void run() {
        try {
            while(true) {
                    CarBody carBody = new CarBody(idRegisterer.getId());
                    carBodyCarDetailStorage.add(carBody);
                    Thread.sleep(producingSpeed);
                    logger.info("New car body has been produced successfully! Its ID is " + carBody.getId());
            }
        } catch (InterruptedException e) {
            logger.fatal("Can not produce car body! Interrupt exception!");
        }

        logger.info("Car Body Producer finished successfully!");
    }
}
