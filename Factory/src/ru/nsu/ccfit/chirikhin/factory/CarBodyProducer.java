package ru.nsu.ccfit.chirikhin.factory;

import org.apache.log4j.Logger;

public class CarBodyProducer extends Producer {
    private Storage<CarBody> carBodyCarDetailStorage;
    private static final Logger logger =  Logger.getLogger(AccessoryProducer.class.getName());

    public CarBodyProducer(Storage<CarBody> carBodyCarDetailStorage, ProducingSpeed producingSpeed) {
        super(producingSpeed);
        this.carBodyCarDetailStorage = carBodyCarDetailStorage;
    }

    @Override
    public void run() {
        while(true) {
            try {
                carBodyCarDetailStorage.add(new CarBody());
                Thread.sleep(getTimeToSleep());
                logger.info("CarBodyProducer: new car body has been produced successfully!");
            } catch (StorageOverflowedException e) {
                logger.error("CarBodyProducer: Can not produce car body. Storage is full");
            } catch (InterruptedException e) {
                logger.fatal("CarBodyProducer: Can not produce car body! Interrupt exception!");
            } catch (DeveloperBugException e) {
                logger.fatal("CarBodyProducer: Can not produce car body! The developer is stupid!");
            }
        }
    }
}
