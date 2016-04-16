package ru.nsu.ccfit.chirikhin.factory;

import org.apache.log4j.Logger;

import static java.lang.Thread.sleep;


public class EngineProducer implements Runnable {

    private final Storage<Engine> engineCarDetailStorage;
    private final Logger logger = Logger.getLogger(AccessoryProducer.class.getName());
    private final IDRegisterer idRegisterer;
    private final int producingSpeed;

    public EngineProducer(Storage<Engine> engineCarDetailStorage, int producingSpeed, IDRegisterer idRegisterer) {
        this.producingSpeed = producingSpeed;
        this.idRegisterer = idRegisterer;
        this.engineCarDetailStorage = engineCarDetailStorage;
    }

    @Override
    public void run() {
        while (true) {
            try {
                engineCarDetailStorage.add(new Engine(idRegisterer));
                logger.info("New engine has been produced successfully");
                sleep(producingSpeed);
            } catch (StorageOverflowedException e) {
                logger.error("Can not produce new engine. Storage is full");
            } catch (InterruptedException e) {
                logger.fatal("Can not produce new engine! Interrupt exception!");
            }

        }
    }
}
