package ru.nsu.ccfit.chirikhin.factory;

import javafx.application.Platform;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

public class FactoryController implements Observer {
    private static final int DEFAULT_PRODUCING_SPEED = 0;
    private static final int MAX_PRODUCING_SPEED = 3000;
    private Logger logger = Logger.getLogger(FactoryController.class.getName());
    private final Factory factory;
    private FXMLViewController fxmlViewController;
    private ConfigParser configParser;

    public FactoryController(String pathToFile, FXMLViewController fxmlViewController) throws InvalidConfigException, IOException, DeveloperBugException, InterruptedException {
        if (null == pathToFile || null == fxmlViewController) {
            throw new IllegalArgumentException("Null reference in constructor!");
        }

        this.fxmlViewController = fxmlViewController;

        fxmlViewController.addObserver(this);
        configParser = new ConfigParser(pathToFile);
        logger.info("Factory Controller has started!");
        factory = new Factory(configParser.getWorkersCount(), configParser.getDealersCount(), configParser.getAccessorySupplCount(), configParser.getCarStorageSize(), configParser.getEngineStorageSize(),
                configParser.getAccessoryStorageSize(), configParser.getCarBodyStorageSize(), configParser.isLog(), DEFAULT_PRODUCING_SPEED);

        factory.setOnEngineStorageChangedHandler(new Handler(fxmlViewController) {
            @Override
            public void update(Observable o, Object arg) {
                Platform.runLater(() -> fxmlViewController.onEngineStorageChanged(((StorageEventContext) arg).getStorageEvent()));
            }
        });

        factory.setOnTaskCompletedHandler(new Handler(fxmlViewController) {
            @Override
            public void update(Observable o, Object arg) {
                TaskState taskState = (TaskState) arg;
                if (TaskState.COMPLETED == taskState) {
                    Platform.runLater(() -> fxmlViewController.onTaskCompleted());
                }
            }
        });

        factory.setOnNewTaskToMakeCarAppeared(new Handler(fxmlViewController) {
            @Override
            public void update(Observable o, Object arg) {
                TaskState taskState = (TaskState) arg;
                if (TaskState.START == taskState) {
                    Platform.runLater(() -> fxmlViewController.onNewTaskToMakeCarAppeared());
                }
            }
        });

        factory.setOnAccessoryStorageChangedHandler(new Handler(fxmlViewController) {
            @Override
            public void update(Observable o, Object arg) {
                Platform.runLater(() -> fxmlViewController.onAccessoryStorageChanged(((StorageEventContext) arg).getStorageEvent()));
            }
        });

        factory.setOnCarBodyStorageChangedHandler(new Handler(fxmlViewController) {
            @Override
            public void update(Observable o, Object arg) {
                Platform.runLater(() -> fxmlViewController.onCarBodyStorageChanged(((StorageEventContext) arg).getStorageEvent()));
            }
        });

        factory.setOnCarStorageChangedHandler(new Handler(fxmlViewController) {
            @Override
            public void update(Observable o, Object arg) {
                Platform.runLater(() -> fxmlViewController.onCarStorageChanged(((StorageEventContext) arg).getStorageEvent()));
            }
        });
    }

    public void startFactory() throws DeveloperBugException, InvalidConfigException, InterruptedException, IOException {
        logger.info("Factory has started!");
        initFXMLView();
        factory.start();
    }

    public void stop() {
        factory.stop();
    }

    public void initFXMLView() {
        Platform.runLater(() -> {
            fxmlViewController.setCountOfAccessoryProducers(configParser.getAccessorySupplCount());
            fxmlViewController.setCountOfDealers(configParser.getDealersCount());
            fxmlViewController.setCountOfWorkers(configParser.getWorkersCount());
            fxmlViewController.setAccessoryStorageSize(configParser.getAccessoryStorageSize());
            fxmlViewController.setEngineStorageSize(configParser.getEngineStorageSize());
            fxmlViewController.setCarBodyStorageSize(configParser.getCarBodyStorageSize());
            fxmlViewController.setCarStorageSize(configParser.getCarStorageSize());
            fxmlViewController.setEngineProducingSpeed(DEFAULT_PRODUCING_SPEED, MAX_PRODUCING_SPEED);
            fxmlViewController.setCarBodyProducingSpeed(DEFAULT_PRODUCING_SPEED, MAX_PRODUCING_SPEED);
            fxmlViewController.setDealerSpeed(DEFAULT_PRODUCING_SPEED, MAX_PRODUCING_SPEED);
            fxmlViewController.setAccessoryProducingSpeed(DEFAULT_PRODUCING_SPEED, MAX_PRODUCING_SPEED);
        });
    }

    @Override
    public void update(Observable o, Object arg) {
        ChangedSpeedEvent changedSpeedEvent = (ChangedSpeedEvent) arg;
        switch (changedSpeedEvent.getSpeedType()) {
            case SELLING_CARS:
                factory.onDealerSellingSpeedChanged(changedSpeedEvent.getNewSpeed());
                break;
            case ENGINE_PRODUCING:
                factory.onEngineProducingSpeedChanged(changedSpeedEvent.getNewSpeed());
                break;
            case ACCESSORY_PRODUCING:
                factory.onAccessoryProducingSpeedChanged(changedSpeedEvent.getNewSpeed());
                break;
            case CAR_BODY_PRODUCING:
                factory.onCarBodyProducingSpeedChanged(changedSpeedEvent.getNewSpeed());
                break;
        }
    }
}
