package ru.nsu.ccfit.chirikhin.factory;

import org.apache.log4j.Logger;
import ru.nsu.ccfit.chirikhin.threadpool.ThreadPool;

import java.util.Observable;
import java.util.Observer;

public class CarCollectors extends Observable {


    public static class CollectorTask implements MyRunnable {

        private final Storage<Engine> engineStorage;
        private final Storage<CarBody> carBodyStorage;
        private final Storage<Accessory> accessoryStorage;
        private final Storage<Car> carStorage;
        private final Logger logger = Logger.getLogger(CollectorTask.class.getName());
        private final IDRegisterer idRegisterer;

        public CollectorTask (Storage<Engine> engineStorage, Storage<CarBody> carBodyStorage, Storage<Accessory> accessoryStorage, Storage<Car> carStorage, IDRegisterer idRegisterer) {
            if (null == engineStorage || null == carBodyStorage || null == accessoryStorage || null == carStorage || null == idRegisterer) {
                throw new IllegalArgumentException("Error! Can't create myself because of null reference!");
            }

            this.idRegisterer = idRegisterer;
            this.engineStorage = engineStorage;
            this.carBodyStorage = carBodyStorage;
            this.accessoryStorage = accessoryStorage;
            this.carStorage = carStorage;
        }

        @Override
        public int run() throws InterruptedException {
            Car car = new Car(engineStorage.getNext(), accessoryStorage.getNext(), carBodyStorage.getNext(), idRegisterer.getId());
            carStorage.add(car);
            logger.info("Car ID " + car.getId() + "(" + car.getBodyId() + " " + car.getEngineId() + " " + car.getAccessoryId() + ") has been made and sent to car storage!");
            return 0;
        }


    }
    private final ThreadPool collectors;
    private final CollectorTask task;

    private final Logger logger = Logger.getLogger(CarCollectors.class.getName());

    public CarCollectors(int collectorsCount, Storage<Engine> engineStorage, Storage<CarBody> carBodyStorage, Storage<Accessory> accessoryStorage,
                         Storage<Car> carStorage, IDRegisterer idRegisterer) throws InterruptedException {
        if (collectorsCount < 0) {
            throw new IllegalArgumentException("Count of workers (collectors) can't be less than zero!");
        }

        if (null == engineStorage || null == carBodyStorage || null == accessoryStorage || null == carStorage || null == idRegisterer) {
            throw new IllegalArgumentException("Null references have been found while constructing CarCollectors");
        }

        task = new CollectorTask(engineStorage, carBodyStorage, accessoryStorage, carStorage, idRegisterer);
        collectors = new ThreadPool(collectorsCount);
    }


    public void makeCar() throws InterruptedException {
        setChanged();
        notifyObservers(TaskState.START);

        TaskContext taskContext = new TaskContext(task, () -> {
            setChanged();
            notifyObservers(TaskState.COMPLETED);
        });

        collectors.addTask(taskContext);
    }

    public void makeCars(int count) throws InterruptedException {
        for (int i = 0; i < count; ++i) {
            makeCar();
        }
    }

    public void stop() throws InterruptedException {
            collectors.stop();
    }
}
