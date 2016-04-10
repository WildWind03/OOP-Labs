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

        public CollectorTask (Storage<Engine> engineStorage, Storage<CarBody> carBodyStorage, Storage<Accessory> accessoryStorage, Storage<Car> carStorage) {
            if (null == engineStorage || null == carBodyStorage || null == accessoryStorage || null == carStorage) {
                throw new IllegalArgumentException("Error! Can't create myself because of null reference!");
            }

            this.engineStorage = engineStorage;
            this.carBodyStorage = carBodyStorage;
            this.accessoryStorage = accessoryStorage;
            this.carStorage = carStorage;
        }

        @Override
        public void run() {
            try {
                Car car = new Car(engineStorage.getNext(), accessoryStorage.getNext(), carBodyStorage.getNext());
                carStorage.add(car);
                logger.info("Car ID" + car.getId() + " has been made and sent to car storage!");
            } catch (StorageOverflowedException e) {
                logger.error(e.toString());
            } catch (InterruptedException e) {
                logger.fatal(e.toString());
            } catch (StorageEmptyException e) {
                logger.fatal(e.toString());
            }
        }


    }
    ThreadPool collectors;
    final CollectorTask task;

    public CarCollectors(int collectorsCount, Storage<Engine> engineStorage, Storage<CarBody> carBodyStorage, Storage<Accessory> accessoryStorage, Storage<Car> carStorage) throws InterruptedException {
        task = new CollectorTask(engineStorage, carBodyStorage, accessoryStorage, carStorage);
        collectors = new ThreadPool(collectorsCount);
    }

    public void makeCar() throws InterruptedException {
        collectors.addTask(task);
    }
}
