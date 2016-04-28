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

    private final Storage<Engine> engineStorage;
    private final Storage<Accessory> accessoryStorage;
    private final Storage<Car> carStorage;
    private final Storage<CarBody> carBodyStorage;

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
    private final Thread carStorageControllerThread;
    private final Thread[] dealersThreads;

    private CarCollectors carCollectors;

    public Factory(int workersCount, int dealersCount, int accessorySupplCount, int carStorageSize, int engineStorageSize, int accessoryStorageSize, int carBodyStorageSize, boolean isLog, int defaultProducingSpeed) throws InterruptedException, IOException, DeveloperBugException {
        if (workersCount < 0 || dealersCount < 0 || accessorySupplCount < 0 || carStorageSize < 0 || engineStorageSize < 0 || accessoryStorageSize < 0 || carBodyStorageSize < 0 || defaultProducingSpeed < 0) {
            throw new IllegalArgumentException("One of sizes in constructor is negative");
        }


        DEFAULT_PRODUCING_SPEED = defaultProducingSpeed;
        engineStorage = new Storage<>(engineStorageSize);
        accessoryStorage = new Storage<>(accessoryStorageSize);
        carBodyStorage = new Storage<>(carBodyStorageSize);
        carStorage = new Storage<>(carStorageSize);

        this.accessorySupplCount = accessorySupplCount;
        this.isLog = isLog;
        this.dealersCount = dealersCount;

        engineProducer = new EngineProducer(engineStorage, DEFAULT_PRODUCING_SPEED, idRegisterer);
        engineProducerThread = new Thread(engineProducer);
        engineProducerThread.setName("Engine Producer Thread");

        accessoryProducers = new AccessoryProducer[accessorySupplCount];
        accessoryProducersThreads = new Thread[accessorySupplCount];
        for (int i = 0; i < accessorySupplCount; ++i) {
            accessoryProducers[i] = new AccessoryProducer(accessoryStorage, "Accessory Producer num " + i, DEFAULT_PRODUCING_SPEED, idRegisterer);
            accessoryProducersThreads[i] = new Thread(accessoryProducers[i]);
            accessoryProducersThreads[i].setName("Accessory Producer Thread " + i);
        }

        dealers = new Dealer[dealersCount];
        dealersThreads = new Thread[dealersCount];
        for (int i = 0; i < dealersCount; ++i) {
            dealers[i] = new Dealer(carStorage, "Dealer " + i, DEFAULT_PRODUCING_SPEED);
            dealersThreads[i] = new Thread(dealers[i]);
            dealersThreads[i].setName("Dealer Thread " + i);
        }

        carBodyProducer = new CarBodyProducer(carBodyStorage, DEFAULT_PRODUCING_SPEED, idRegisterer);
        carBodyProducerThread = new Thread(carBodyProducer);
        carBodyProducerThread.setName("Car Body Producer Thread");


        try {
            carCollectors = new CarCollectors(workersCount, engineStorage, carBodyStorage, accessoryStorage, carStorage, idRegisterer);
        } catch (InterruptedException e) {
            logger.fatal("Interrupt exception");
            throw e;
        }

        carStorageController = new CarStorageController(carStorage, carCollectors);
        carStorageControllerThread = new Thread(carStorageController);
        carStorageControllerThread.setName("Car Storage Controller");
    }

    public void setOnEngineStorageChangedHandler(Handler handler) {
        if (null == handler) {
            throw new NullPointerException("Handler can't be null reference");
        }

        engineStorage.addObserver(handler);
    }

    public void onEngineProducingSpeedChanged(int newSpeed) {
        if (newSpeed < 0) {
            throw new IllegalArgumentException("Engine producing speed can not be negative!");
        }
        engineProducer.changeProducingSpeed(newSpeed);
    }

    public void onAccessoryProducingSpeedChanged(int newSpeed) {
        if (newSpeed < 0) {
            throw new IllegalArgumentException("Accessory producing speed can not be negative!");
        }

        for (int i = 0; i < accessorySupplCount; ++i)  {
            accessoryProducers[i].changeProducingSpeed(newSpeed);
        }
    }

    public void onCarBodyProducingSpeedChanged(int newSpeed) {
        if (newSpeed < 0) {
            throw new IllegalArgumentException("CarBody producing speed can not be negative!");
        }
        carBodyProducer.changeProducingSpeed(newSpeed);
    }

    public void onDealerSellingSpeedChanged(int newSpeed) {
        if (newSpeed < 0) {
            throw new IllegalArgumentException("Dealers selling speed can not be negative!");
        }

        for (int k= 0; k < dealersCount; ++k) {
            dealers[k].setSellingSpeed(newSpeed);
        }
    }

    public void setOnTaskCompletedHandler(Handler handler) {
        if (null == handler) {
            throw new IllegalArgumentException("Handler reference can not be null!");
        }
        carCollectors.addObserver(handler);
    }

    public void setOnAccessoryStorageChangedHandler(Handler handler) {
        if (null == handler) {
            throw new IllegalArgumentException("Handler reference can not be null!");
        }
        accessoryStorage.addObserver(handler);
    }

    public void setOnCarBodyStorageChangedHandler(Handler handler) {
        if (null == handler) {
            throw new IllegalArgumentException("Handler reference can not be null!");
        }
        carBodyStorage.addObserver(handler);
    }

    public void setOnCarStorageChangedHandler(Handler handler) {
        if (null == handler) {
            throw new IllegalArgumentException("Handler reference can not be null!");
        }
        carStorage.addObserver(handler);
    }

    public void setOnNewTaskToMakeCarAppeared(Handler handler) {
        if (null == handler) {
            throw new IllegalArgumentException("Handler reference can not be null!");
        }
        carCollectors.addObserver(handler);
    }

    public void start() throws InterruptedException {
            for (int k = 0; k < accessorySupplCount; ++k) {
                accessoryProducersThreads[k].start();
            }

            engineProducerThread.start();
            carBodyProducerThread.start();
            carStorageControllerThread.start();

            for (int k = 0; k < dealersCount; ++k) {
                dealersThreads[k].start();
            }
    }

    public void stop() {
        for (int k = 0; k < accessorySupplCount; ++k) {
            accessoryProducersThreads[k].interrupt();
        }

        engineProducerThread.interrupt();

        carBodyProducerThread.interrupt();
        carStorageControllerThread.interrupt();

        for (int k = 0; k < dealersCount; ++k) {
            dealersThreads[k].interrupt();
        }

        for (int k = 0; k < accessorySupplCount; ++k) {
            accessoryProducersThreads[k].interrupt();
        }

        try {
            carCollectors.stop();
            engineProducerThread.join();
            engineProducerThread.join();
            carBodyProducerThread.join();
            carStorageControllerThread.join();
            for (int k = 0; k < dealersCount; ++k) {
                dealersThreads[k].join();
            }
        } catch (InterruptedException e) {
            logger.fatal("Interrupted exception");
        }
    }
}
