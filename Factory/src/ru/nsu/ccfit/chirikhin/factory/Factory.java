package ru.nsu.ccfit.chirikhin.factory;

import jdk.nashorn.internal.runtime.regexp.joni.Config;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

public class Factory extends Observable {
    public static final long DEFAULT_PRODUCING_SPEED = 1000;

    private final Logger logger = Logger.getLogger(Factory.class.getName());
    private final String pathToConfig;
    private final IDRegisterer idRegisterer = new IDRegisterer();

    private Storage<Engine> engineStorage;

    public Factory(String pathToConfig) throws InvalidConfigException, IOException, DeveloperBugException {
        this.pathToConfig = pathToConfig;
        ConfigParser configParser = new ConfigParser(pathToConfig);
        engineStorage = new Storage<>(configParser.getEngineStorageSize());
    }

    public void setOnEngineStorageChangedHandler(OnEngineStorageChangedHandler handler) {
        engineStorage.addObserver(handler);
        logger.debug("Engine Handler has been successfully added as observer to engine storage!");
    }

    public void start() throws DeveloperBugException, InvalidConfigException, InterruptedException, IOException {
        ConfigParser configParser;

        try {
            configParser = new ConfigParser(pathToConfig);

            Storage<Accessory> accessoryStorage = new Storage<>(configParser.getAccessoryStorageSize());

            Storage<CarBody> carBodyStorage = new Storage<>(configParser.getCarBodyStorageSize());

            //engineStorage = new Storage<>(configParser.getEngineStorageSize());

            Storage<Car> carStorage = new Storage<>(configParser.getCarStorageSize());


            Thread accessoryProducers[] = new Thread[configParser.getAccessorySupplCount()];
            for (int k = 0; k < configParser.getAccessorySupplCount(); ++k) {
                accessoryProducers[k] = new Thread(new AccessoryProducer(accessoryStorage, "Accessory Producer num " + k, DEFAULT_PRODUCING_SPEED, idRegisterer));
                accessoryProducers[k].start();
            }

            Thread engineProducer = new Thread (new EngineProducer(engineStorage, DEFAULT_PRODUCING_SPEED, idRegisterer));
            engineProducer.start();

            Thread carBodyProducer = new Thread(new CarBodyProducer(carBodyStorage, DEFAULT_PRODUCING_SPEED, idRegisterer));
            carBodyProducer.start();

            CarCollectors carCollectors = new CarCollectors(configParser.getWorkersCount(), engineStorage, carBodyStorage, accessoryStorage, carStorage, idRegisterer);
            CarStorageController carStorageController = new CarStorageController(carStorage, carCollectors);

            Thread carStorageCollectorThread = new Thread(carStorageController);
            carStorageCollectorThread.start();


            Thread dealerThread[] = new Thread[configParser.getDealersCount()];
            for (int k = 0; k < configParser.getDealersCount(); ++k) {
                dealerThread[k] = new Thread(new Dealer(carStorage, "Dealer " + k));
                dealerThread[k].start();
            }
        } catch(Exception e) {
            logger.error(e.toString());
            throw e;
        }


    }

    public void stop() {

    }
}
