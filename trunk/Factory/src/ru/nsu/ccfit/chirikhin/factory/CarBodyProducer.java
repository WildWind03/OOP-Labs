package ru.nsu.ccfit.chirikhin.factory;

import org.apache.log4j.Logger;

public class CarBodyProducer implements Runnable {

    private final Storage<CarBody> carBodyCarDetailStorage;
    private final Logger logger =  Logger.getLogger(AccessoryProducer.class.getName());
    private final IDRegisterer idRegisterer;
    private final long producingSpeed;

    public CarBodyProducer(Storage<CarBody> carBodyCarDetailStorage, long producingSpeed, IDRegisterer idRegisterer) {

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
        while(true) {
            try {
                CarBody carBody = new CarBody(idRegisterer);
                carBodyCarDetailStorage.add(carBody);
                Thread.sleep(producingSpeed);
                logger.info("New car body has been produced successfully! Its ID is " + carBody.getId());
            } catch (StorageOverflowedException e) {
                logger.error("Can not produce car body. Storage is full");
            } catch (InterruptedException e) {
                logger.fatal("Can not produce car body! Interrupt exception!");
            }
        }
    }
}
