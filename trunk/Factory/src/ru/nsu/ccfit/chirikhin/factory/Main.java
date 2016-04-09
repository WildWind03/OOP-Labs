package ru.nsu.ccfit.chirikhin.factory;

import org.apache.log4j.Logger;

public class Main {

    public static ProducingSpeed DEFAULT_PRODUCING_SPEED = ProducingSpeed.NORMAL;

    public static void main(String[] args) {
        Logger logger = Logger.getLogger(Main.class.getName());
        ConfigParser configParser;

        try {
            configParser = new ConfigParser("./config.txt");

            Storage<Accessory> accessoryStorage = new Storage<>(configParser.getAccessoryStorageSize());
            Storage<CarBody> carBodyStorage = new Storage<>(configParser.getCarBodyStorageSize());
            Storage<Engine> engineStorage = new Storage<>(configParser.getEngineStorageSize());
            Storage<Car> carStorage = new Storage<>(configParser.getCarStorageSize());

            for (int k = 0; k < configParser.getAccessorySupplCount(); ++k) {
                Thread accessoryProducer = new Thread(new AccessoryProducer(accessoryStorage, DEFAULT_PRODUCING_SPEED));
            }

            Thread engineProducer = new Thread (new EngineProducer(engineStorage, DEFAULT_PRODUCING_SPEED));
            Thread carBodyProducer = new Thread(new CarBodyProducer(carBodyStorage, DEFAULT_PRODUCING_SPEED));

            CarCollectors carCollectors = new CarCollectors(configParser.getWorkersCount(), engineStorage, carBodyStorage, accessoryStorage, carStorage);
            CarStorageController carStorageController = new CarStorageController(carStorage, carCollectors);


        } catch(Exception e) {
            logger.error(e.toString());
        }


    }
}
