package ru.nsu.ccfit.chirikhin.factory;

import org.apache.log4j.Logger;
import ru.nsu.ccfit.chirikhin.threadpool.ThreadPool;

public class CarCollectors {

    public static class CollectorTask implements Runnable{

        Storage<Engine> engineStorage;
        Storage<CarBody> carBodyStorage;
        Storage<Accessory> accessoryStorage;

        Storage<Car> carStorage;

        Logger logger = Logger.getLogger(CollectorTask.class.getName());

        public CollectorTask() {

        }

        public CollectorTask (Storage<Engine> engineStorage, Storage<CarBody> carBodyStorage, Storage<Accessory> accessoryStorage, Storage<Car> carStorage) {
            this.engineStorage = engineStorage;
            this.carBodyStorage = carBodyStorage;
            this.accessoryStorage = accessoryStorage;
            this.carStorage = carStorage;
        }

        @Override
        public void run() {
            try {
                carStorage.add(new Car(engineStorage.getNext(), accessoryStorage.getNext(), carBodyStorage.getNext()));
            } catch (StorageOverflowedException e) {
                logger.error(e.toString());
            } catch (InterruptedException e) {
                logger.error(e.toString());
            } catch (StorageEmptyException e) {
                logger.error(e.toString());
            }
        }


    }
    ThreadPool collectors;
    CollectorTask task;

    public CarCollectors(int collectorsCount, Storage<Engine> engineStorage, Storage<CarBody> carBodyStorage, Storage<Accessory> accessoryStorage, Storage<Car> carStorage) throws InterruptedException {
        collectors = new ThreadPool(collectorsCount);
        task = new CollectorTask(engineStorage, carBodyStorage, accessoryStorage, carStorage);
    }

    public void makeCar() throws InterruptedException {
        collectors.addTask(task);
    }
}
