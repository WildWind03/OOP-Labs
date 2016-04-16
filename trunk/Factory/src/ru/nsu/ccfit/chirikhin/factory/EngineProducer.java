package ru.nsu.ccfit.chirikhin.factory;

import org.apache.log4j.Logger;

import static java.lang.Thread.sleep;


public class EngineProducer implements Runnable {

    private final Storage<Engine> engineCarDetailStorage;
    private final Logger logger = Logger.getLogger(AccessoryProducer.class.getName());
    private final IDRegisterer idRegisterer;
    private final long producingSpeed;

    public EngineProducer(Storage<Engine> engineCarDetailStorage, long producingSpeed, IDRegisterer idRegisterer) {
        this.producingSpeed = producingSpeed;
        this.idRegisterer = idRegisterer;
        this.engineCarDetailStorage = engineCarDetailStorage;
        logger.info ("Engine producers has been created!");
    }

    @Override
    public void run() {
        while (true) {
            try {
                Engine engine = new Engine(idRegisterer.getId());
                engineCarDetailStorage.add(engine);
                logger.info("New engine has been produced successfully! It's ID is " + engine.getId());
                sleep(producingSpeed);
            } catch (StorageOverflowedException e) {
                logger.error("Can not produce new engine. Storage is full");
            } catch (InterruptedException e) {
                logger.fatal("Can not produce new engine! Interrupt exception!");
            }

        }
    }
}
