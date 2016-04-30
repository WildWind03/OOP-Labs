package ru.nsu.ccfit.chirikhin.factory;

import org.apache.log4j.Logger;
import ru.nsu.ccfit.chirikhin.blockingqueue.BlockingQueue;

import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.atomic.AtomicInteger;

public class CarStorageController implements Runnable, Observer {
    private enum CarStorageControllerEventType {
        STORAGE_ADD, STORAGE_GET, TASK_ADD, TASK_COMPLETED
    }
    private static class CarStorageControllerEvent {
        CarStorageControllerEventType eventType;
        Object object;

        public CarStorageControllerEvent(CarStorageControllerEventType eventType, Object object) {
            this.eventType = eventType;
            this.object = object;
        }

        public CarStorageControllerEventType getEventType() {
            return eventType;
        }

        public Object getObject() {
            return object;
        }
    }
    private final static int MAX_EVENT_COUNTS = 40000;
    private Storage<Car> carStorage;
    private CarCollectors carCollectors;
    private Logger logger = Logger.getLogger(CarStorageController.class.getName());
    private BlockingQueue<CarStorageControllerEvent> events;

    public CarStorageController(Storage<Car> carStorage, CarCollectors carCollectors) {
        if (null == carCollectors || null == carStorage) {
            throw new IllegalArgumentException("Null refrence in constructor");
        }

        events = new BlockingQueue<>(MAX_EVENT_COUNTS);

        carStorage.addObserver(this);
        this.carStorage = carStorage;
        this.carCollectors = carCollectors;
    }

    @Override
    public void run() {
        int countOfCurrentTasksInQueue = 0;

        try {
            carCollectors.makeCar();
            countOfCurrentTasksInQueue++;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while(true) {
            try {
                CarStorageControllerEvent event = events.pop();
                switch(event.getEventType()) {
                    case TASK_COMPLETED:
                        countOfCurrentTasksInQueue--;
                        break;
                    case STORAGE_GET:
                        int storageFullness = (int) event.getObject();
                        if (storageFullness + countOfCurrentTasksInQueue < carStorage.getMaxSize()) {
                            carCollectors.makeCars(carStorage.getMaxSize() / 2);
                            countOfCurrentTasksInQueue+=carStorage.getMaxSize() / 2;
                        }
                        break;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        CarStorageControllerEventType carStorageControllerEventType = null;
        Object object = null;

        if (o.getClass() == Storage.class) {
                StorageEventContext storageEventContext = (StorageEventContext) arg;

                object = storageEventContext.getCurrentFullness();

                if (storageEventContext.getStorageEvent() == StorageEvent.GET) {
                    carStorageControllerEventType = CarStorageControllerEventType.STORAGE_GET;
                } else {
                    carStorageControllerEventType = CarStorageControllerEventType.STORAGE_ADD;
                }
            } else {
            if (o.getClass() == CarCollectors.class) {
                TaskState taskState= (TaskState) arg;

                switch(taskState) {
                    case COMPLETED:
                        carStorageControllerEventType = CarStorageControllerEventType.TASK_COMPLETED;
                        break;
                    case START:
                        carStorageControllerEventType = CarStorageControllerEventType.TASK_ADD;
                        break;
                }

                object = null;
            }
        }

        try {
            events.put(new CarStorageControllerEvent(carStorageControllerEventType, object));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
