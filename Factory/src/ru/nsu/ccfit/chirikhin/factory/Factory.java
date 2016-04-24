package ru.nsu.ccfit.chirikhin.factory;

import jdk.nashorn.internal.runtime.regexp.joni.Config;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

public class Factory {
    public final int DEFAULT_PRODUCING_SPEED;

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

    private final EngineProducer engineProducer;
    private final AccessoryProducer[] accessoryProducers;
    private final CarBodyProducer carBodyProducer;
    private final Dealer dealers[];
    private final CarStorageController carStorageController;

    private final Thread[] accessoryProducersThreads;
    private final Thread engineProducerThread;
    private final Thread carBodyProducerThread;
    private final Thread carStorageCollectorThread;
    private final Thread[] dealersThreads;

    private CarCollectors carCollectors;

    public Factory(int workersCount, int dealersCount, int accessorySupplCount, int carStorageSize, int engineStorageSize, int accessoryStorageSize, int carBodyStorageSize, boolean isLog, int defaultProducingSpeed) throws InvalidConfigException, IOException, DeveloperBugException {
        DEFAULT_PRODUCING_SPEED = defaultProducingSpeed;
        engineStorage = new Storage<>(engineStorageSize);
        accessoryStorage = new Storage<>(accessoryStorageSize);
        carBodyStorage = new Storage<>(carBodyStorageSize);
        carStorage = new Storage<>(carStorageSize);

        this.accessorySupplCount = accessorySupplCount;
        this.workersCount = workersCount;
        this.isLog = isLog;
        this.dealersCount = dealersCount;

        engineProducer = new EngineProducer(engineStorage, DEFAULT_PRODUCING_SPEED, idRegisterer);
        engineProducerThread = new Thread(engineProducer);

        accessoryProducers = new AccessoryProducer[accessorySupplCount];
        accessoryProducersThreads = new Thread[accessorySupplCount];
        for (int i = 0; i < accessorySupplCount; ++i) {
            accessoryProducers[i] = new AccessoryProducer(accessoryStorage, "Accessory Producer num " + i, DEFAULT_PRODUCING_SPEED, idRegisterer);
            accessoryProducersThreads[i] = new Thread(accessoryProducers[i]);
        }

        dealers = new Dealer[dealersCount];
        dealersThreads = new Thread[dealersCount];
        for (int i = 0; i < dealersCount; ++i) {
            dealers[i] = new Dealer(carStorage, "Dealer " + i);
            dealersThreads[i] = new Thread(dealers[i]);
        }

        carBodyProducer = new CarBodyProducer(carBodyStorage, DEFAULT_PRODUCING_SPEED, idRegisterer);
        carBodyProducerThread = new Thread(carBodyProducer);


        try {
            carCollectors = new CarCollectors(workersCount, engineStorage, carBodyStorage, accessoryStorage, carStorage, idRegisterer);
        } catch (InterruptedException e) {
            logger.fatal("Interrupt exception");
        }

        carStorageController = new CarStorageController(carStorage, carCollectors);
        carStorageCollectorThread = new Thread(carStorageController);
    }

    public void setOnEngineStorageChangedHandler(Handler handler) {
        engineStorage.addObserver(handler);
        logger.debug("Engine Handler has been successfully added as observer to engine storage!");
    }

    public void onEngineProducingSpeedChanged(int newSpeed) {
        engineProducer.changeProducingSpeed(newSpeed);
    }

    public void onAccessoryProducingSpeedChanged(int newSpeed) {
        for (int i = 0; i < accessorySupplCount; ++i)  {
            accessoryProducers[i].changeProducingSpeed(newSpeed);
        }
    }

    public void onCarBodyProducingSpeedChanged(int newSpeed) {
        carBodyProducer.changeProducingSpeed(newSpeed);
    }

    public void onDealerSellingSpeedChnaged(int newSpeed) {
        for (int k= 0; k < dealersCount; ++k) {
            dealers[k].setSellingSpeed(newSpeed);
        }
    }

    public void setOnTaskCompletedHandler(Handler handler) {
        carCollectors.addObserver(handler);
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

    public void setOnNewTaskToMakeCarAppeared(Handler handler) {
        carCollectors.addObserver(handler);
    }

    public void start() throws DeveloperBugException, InvalidConfigException, InterruptedException, IOException {
        try {

            for (int k = 0; k < accessorySupplCount; ++k) {
                accessoryProducersThreads[k].start();
            }

            engineProducerThread.start();
            carBodyProducerThread.start();
            carStorageCollectorThread.start();

            for (int k = 0; k < dealersCount; ++k) {
                dealersThreads[k].start();
            }
        } catch(Exception e) {
            logger.error(e.toString());
            throw e;
        }


    }

    public void stop() {
        /*for (int k = 0; k < accessorySupplCount; ++k) {
            accessoryProducers[k].kill();
        }

        engineProducer.kill();
        carBodyProducer.kill();
        carStorageController.kill();

        for (int k = 0; k < dealersCount; ++k) {
            dealers[k].kill();
        }

        try {
            carCollectors.kill();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        */
    }
}
