package ru.nsu.ccfit.chirikhin.factory;

import org.apache.log4j.Logger;

import java.util.Observable;

public class Factory extends Observable {
    public static final ProducingSpeed DEFAULT_PRODUCING_SPEED = ProducingSpeed.NORMAL;

    private final Logger logger = Logger.getLogger(Factory.class.getName());
    private final String pathToConfig;
    private final IDRegisterer idRegisterer = new IDRegisterer();

    public Factory(String pathToConfig) {
        this.pathToConfig = pathToConfig;
    }

    public void start() {
        ConfigParser configParser;

        try {
            configParser = new ConfigParser(pathToConfig);

            Storage<Accessory> accessoryStorage = new Storage<>(configParser.getAccessoryStorageSize());
            Storage<CarBody> carBodyStorage = new Storage<>(configParser.getCarBodyStorageSize());
            Storage<Engine> engineStorage = new Storage<>(configParser.getEngineStorageSize());
            Storage<Car> carStorage = new Storage<>(configParser.getCarStorageSize());


            Thread accessoryProducers[] = new Thread[configParser.getAccessorySupplCount()];
            for (int k = 0; k < configParser.getAccessorySupplCount(); ++k) {
                accessoryProducers[k] = new Thread(new AccessoryProducer(accessoryStorage, "Accessory Producer num " + k, DEFAULT_PRODUCING_SPEED));
            }

            Thread engineProducer = new Thread (new EngineProducer(engineStorage, DEFAULT_PRODUCING_SPEED));
            Thread carBodyProducer = new Thread(new CarBodyProducer(carBodyStorage, DEFAULT_PRODUCING_SPEED));

            CarCollectors carCollectors = new CarCollectors(configParser.getWorkersCount(), engineStorage, carBodyStorage, accessoryStorage, carStorage);
            CarStorageController carStorageController = new CarStorageController(carStorage, carCollectors);

            Thread carStorageCollectorThread = new Thread(carStorageController);


            Thread dealerThread[] = new Thread[configParser.getDealersCount()];
            for (int k = 0; k < configParser.getDealersCount(); ++k) {
                dealerThread[k] = new Thread(new Dealer(carStorage, "Dealer " + k));
            }
        } catch(Exception e) {
            logger.error(e.toString());
        }


    }

    public void stop() {

    }
}
