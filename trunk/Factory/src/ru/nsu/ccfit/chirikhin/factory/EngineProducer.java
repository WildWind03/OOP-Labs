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

    private boolean isRunning;

    public EngineProducer(Storage<Engine> engineCarDetailStorage, int producingSpeed, IDRegisterer idRegisterer) {
        isRunning = true;
        this.producingSpeed = producingSpeed;
        this.idRegisterer = idRegisterer;
        this.engineCarDetailStorage = engineCarDetailStorage;
        logger.info ("Engine producers has been created!");
    }

    public void changeProducingSpeed(int producingSpeed) {
        this.producingSpeed = producingSpeed;
    }

    @Override
    public void run() {
        while (isRunning) {
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

    @Override
    public void update(Observable o, Object arg) {
        changeProducingSpeed((int) arg);
    }

    public void kill() {
        isRunning = false;
    }
}
