package ru.nsu.ccfit.chirikhin.factory;

import jdk.nashorn.internal.runtime.regexp.joni.Config;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

public class Factory extends Observable {
    public static final long DEFAULT_PRODUCING_SPEED = 1000;

    private final Logger logger = Logger.getLogger(Factory.class.getName());
    private final IDRegisterer idRegisterer = new IDRegisterer();

    private Storage<Engine> engineStorage;
    private Storage<Accessory> accessoryStorage;
    private Storage<Car> carStorage;
    private Storage<CarBody> carBodyStorage;

    private int workersCount;
    private int dealersCount;
    private int accessorySupplCount;
    private boolean isLog;

    public Factory(int workersCount, int dealersCount, int accessorySupplCount, int carStorageSize, int engineStorageSize, int accessoryStorageSize, int carBodyStorageSize, boolean isLog) throws InvalidConfigException, IOException, DeveloperBugException {
        engineStorage = new Storage<>(engineStorageSize);
        accessoryStorage = new Storage<>(accessoryStorageSize);
        carBodyStorage = new Storage<>(carBodyStorageSize);
        carStorage = new Storage<>(carStorageSize);

        this.accessorySupplCount = accessorySupplCount;
        this.workersCount = workersCount;
        this.isLog = isLog;
        this.dealersCount = dealersCount;
    }

    public void setOnEngineStorageChangedHandler(Handler handler) {
        engineStorage.addObserver(handler);
        logger.debug("Engine Handler has been successfully added as observer to engine storage!");
    }

    public void setOnAccessoryStorageChangedHandler(Handler handler) {
        accessoryStorage.addObserver(handler);
    }

    public void setOnCarBodyStorageChangedHandler(Handler handler) {
        carBodyStorage.addObserver(handler);
    }

    public void setOnCarStorageChangedHandler(Handler handler) {
        carStorage.addObserver(handler);
    }

    public void start() throws DeveloperBugException, InvalidConfigException, InterruptedException, IOException {
        try {

            Thread accessoryProducers[] = new Thread[accessorySupplCount];
            for (int k = 0; k < accessorySupplCount; ++k) {
                accessoryProducers[k] = new Thread(new AccessoryProducer(accessoryStorage, "Accessory Producer num " + k, DEFAULT_PRODUCING_SPEED, idRegisterer));
                accessoryProducers[k].start();
            }

            Thread engineProducer = new Thread (new EngineProducer(engineStorage, DEFAULT_PRODUCING_SPEED, idRegisterer));
            engineProducer.start();

            Thread carBodyProducer = new Thread(new CarBodyProducer(carBodyStorage, DEFAULT_PRODUCING_SPEED, idRegisterer));
            carBodyProducer.start();

            CarCollectors carCollectors = new CarCollectors(workersCount, engineStorage, carBodyStorage, accessoryStorage, carStorage, idRegisterer);
            CarStorageController carStorageController = new CarStorageController(carStorage, carCollectors);

            Thread carStorageCollectorThread = new Thread(carStorageController);
            carStorageCollectorThread.start();


            Thread dealerThread[] = new Thread[dealersCount];
            for (int k = 0; k < dealersCount; ++k) {
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
