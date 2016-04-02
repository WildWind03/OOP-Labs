package ru.nsu.ccfit.chirikhin.factory;

import org.apache.log4j.Logger;

import static java.lang.Thread.*;

public class EngineProducer extends Producer {

    CarDetailStorage<Engine> engineCarDetailStorage;

    private static final Logger logger = Logger.getLogger(AccessoryProducer.class.getName());

    public EngineProducer(ProducingSpeed producingSpeed, CarDetailStorage<Engine> engineCarDetailStorage) {
        super(producingSpeed);
        this.engineCarDetailStorage = engineCarDetailStorage;
        logger.info("EngineProducer: New engine has been produced successfully!");
    }

    @Override
    public void run() {
        while (true) {
            try {
                engineCarDetailStorage.addDetail(new Engine());
                sleep(getTimeToSleep());
            } catch (StorageOverflowedException e) {
                logger.error("EngineProducer: Can not produce new engine. Storage is full");
            } catch (InterruptedException e) {
                logger.fatal("EngineProducer: Can not produce new engine! Interrupt exception!");
            } catch (DeveloperBugException e) {
                logger.fatal("EngineProducer: Can not produce new engine! The developer is stupid!");
            }

        }
    }
}
