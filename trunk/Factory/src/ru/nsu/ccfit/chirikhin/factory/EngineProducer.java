package ru.nsu.ccfit.chirikhin.factory;

import org.apache.log4j.Logger;

import java.util.Observable;
import java.util.Observer;

import static java.lang.Thread.sleep;


public class EngineProducer implements Observer, Runnable{

    private final Storage<Engine> engineCarDetailStorage;
    private final Logger logger = Logger.getLogger(AccessoryProducer.class.getName());
    private final IDRegisterer idRegisterer;
    private int producingSpeed;

    public EngineProducer(Storage<Engine> engineCarDetailStorage, int producingSpeed, IDRegisterer idRegisterer) {
        if (null == engineCarDetailStorage || null == idRegisterer) {
            throw new IllegalArgumentException("Null references in constructor!");
        }

        if (producingSpeed < 0) {
            throw new IllegalArgumentException("Producing speed is negative!");
        }
        this.producingSpeed = producingSpeed;
        this.idRegisterer = idRegisterer;
        this.engineCarDetailStorage = engineCarDetailStorage;
        logger.info ("Engine producer has been created!");
    }

    public void changeProducingSpeed(int producingSpeed) {
        if (producingSpeed < 0) {
            throw new IllegalArgumentException("Producing speed is negative!");
        }

        this.producingSpeed = producingSpeed;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Engine engine = new Engine(idRegisterer.getId());
                engineCarDetailStorage.add(engine);
                logger.info("New engine has been produced successfully! It's ID is " + engine.getId());
                sleep(producingSpeed);
            }
        } catch (InterruptedException e) {
            logger.fatal("Can not produce new engine! Interrupt exception!");
        }

        logger.info("Engine Producer finished successfully!");
    }

    @Override
    public void update(Observable o, Object arg) {
        changeProducingSpeed((int) arg);
    }
}
