package ru.nsu.ccfit.chirikhin.factory;

import org.apache.log4j.Logger;

public class CarBodyProducer extends Producer {
    private Storage<CarBody> carBodyCarDetailStorage;
    private static final Logger logger =  Logger.getLogger(AccessoryProducer.class.getName());

    public CarBodyProducer(Storage<CarBody> carBodyCarDetailStorage, ProducingSpeed producingSpeed) {
        super(producingSpeed);

        if (null == carBodyCarDetailStorage || null == producingSpeed) {
            String text = "Can not create myself because of null reference!";
            throw new IllegalArgumentException(text);
        }
        this.carBodyCarDetailStorage = carBodyCarDetailStorage;
    }

    @Override
    public void run() {
        while(true) {
            try {
                CarBody carBody = new CarBody();
                carBodyCarDetailStorage.add(carBody);
                Thread.sleep(getTimeToSleep());
                logger.info("New car body has been produced successfully! Its ID is " + carBody.getId());
            } catch (StorageOverflowedException e) {
                logger.error("Can not produce car body. Storage is full");
            } catch (InterruptedException e) {
                logger.fatal("Can not produce car body! Interrupt exception!");
            } catch (DeveloperBugException e) {
                logger.fatal("Can not produce car body! The developer is stupid!");
            }
        }
    }
}
