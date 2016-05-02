package ru.nsu.ccfit.chirikhin.factory;

import org.apache.log4j.Logger;
import ru.nsu.ccfit.chirikhin.blockingqueue.BlockingQueue;

import java.util.Observable;
import java.util.Observer;

public class CarStorageController extends Observable implements Runnable, Observer {
    private enum CarStorageControllerEventType {
        STORAGE_ADD, STORAGE_GET, TASK_ADD, TASK_COMPLETED
    }

    private static class CarStorageControllerEvent {
        private final CarStorageControllerEventType eventType;
        private final int curSize;
        private final int maxSize;

        public CarStorageControllerEvent(CarStorageControllerEventType eventType, int curSize, int maxSize) {
            if (null == eventType) {
                throw new NullPointerException("Null references");
            }

            if (curSize < 0 || maxSize <= 0) {
                throw new IllegalArgumentException("Invalid sizes");
            }

            this.curSize = curSize;
            this.maxSize = maxSize;

            this.eventType = eventType;
        }

        public CarStorageControllerEventType getEventType() {
            return eventType;
        }

        public int getCurSize() {
            return curSize;
        }

        public int getMaxSize() {
            return maxSize;
        }
    }
    private final int MAX_EVENT_COUNTS = 40000;
    private final CarCollectors carCollectors;
    private final static Logger logger = Logger.getLogger(CarStorageController.class.getName());
    private final BlockingQueue<CarStorageControllerEvent> events;

    public CarStorageController(CarCollectors carCollectors) {
        if (null == carCollectors) {
            throw new NullPointerException("Null reference in constructor");
        }

        events = new BlockingQueue<>(MAX_EVENT_COUNTS);
        this.carCollectors = carCollectors;
    }

    @Override
    public void run() {
        int countOfCurrentTasksInQueue = 0;

        try {
            carCollectors.makeCar();
            countOfCurrentTasksInQueue++;

            setChanged();
            notifyObservers(countOfCurrentTasksInQueue);
        } catch (InterruptedException e) {
            logger.debug("Interrupt exception");
        }

        try {
            while(true) {
                CarStorageControllerEvent event = events.pop();
                switch (event.getEventType()) {
                    case STORAGE_ADD:
                        countOfCurrentTasksInQueue--;

                        setChanged();
                        notifyObservers(countOfCurrentTasksInQueue);
                        break;
                    case STORAGE_GET:
                        int storageFullness = event.getCurSize();
                        int maxStorageFullness = event.getMaxSize();

                        if (storageFullness + countOfCurrentTasksInQueue < maxStorageFullness / 2) {
                            carCollectors.makeCars(maxStorageFullness / 2);
                            countOfCurrentTasksInQueue += maxStorageFullness / 2;
                        } else {
                            if (1 == maxStorageFullness) {
                                carCollectors.makeCar();
                                countOfCurrentTasksInQueue++;
                            }
                        }

                        setChanged();
                        notifyObservers(countOfCurrentTasksInQueue);
                        break;
                }
            }
        } catch (InterruptedException e) {
            logger.debug("Interrupt exception");
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        if (null == o || null == arg) {
            throw new NullPointerException("Null references");
        }

        CarStorageControllerEventType carStorageControllerEventType = null;
        int curSize = 1;
        int maxSize = 1;

        if (o.getClass() == Storage.class) {
                StorageEventContext storageEventContext = (StorageEventContext) arg;

                curSize = storageEventContext.getCurrentFullness();
                maxSize = storageEventContext.getMaxSize();

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
            }
        }

        try {
            events.put(new CarStorageControllerEvent(carStorageControllerEventType, curSize, maxSize));
        } catch (InterruptedException e) {
            logger.debug("Interrupt exception!");
        }
    }
}
