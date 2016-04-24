package ru.nsu.ccfit.chirikhin.factory;/**
 * Created by wild_wind on 16.04.16.
 */

import javafx.application.Platform;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Observable;

public class FactoryController {
    private Logger logger = Logger.getLogger(FactoryController.class.getName());
    private final Factory factory;
    private FXMLViewController fxmlViewController;
    private ConfigParser configParser;

    public FactoryController(String pathToFile, FXMLViewController fxmlViewController) throws InvalidConfigException, IOException, DeveloperBugException{
        this.fxmlViewController = fxmlViewController;
        configParser = new ConfigParser(pathToFile);
        logger.info("------------------------ Factory Controller has started!");
        factory = new Factory(configParser.getWorkersCount(), configParser.getDealersCount(), configParser.getAccessorySupplCount(), configParser.getCarStorageSize(), configParser.getEngineStorageSize(),
                configParser.getAccessoryStorageSize(), configParser.getCarBodyStorageSize(), configParser.isLog());

        factory.setOnEngineStorageChangedHandler(new Handler(fxmlViewController) {
            @Override
            public void update(Observable o, Object arg) {
                Platform.runLater(() -> fxmlViewController.onEngineStorageChanged((Storage.StorageContext) arg));
            }
        });

        factory.setOnAccessoryStorageChangedHandler(new Handler(fxmlViewController) {
            @Override
            public void update(Observable o, Object arg) {
                Platform.runLater(() -> fxmlViewController.onAccessoryStorageChanged((Storage.StorageContext) arg));
            }
        });

        factory.setOnCarBodyStorageChangedHandler(new Handler(fxmlViewController) {
            @Override
            public void update(Observable o, Object arg) {
                Platform.runLater(() -> fxmlViewController.onCarBodyStorageChanged((Storage.StorageContext) arg));
            }
        });

        factory.setOnCarStorageChangedHandler(new Handler(fxmlViewController) {
            @Override
            public void update(Observable o, Object arg) {
                Platform.runLater(() -> fxmlViewController.onCarStorageChanged((Storage.StorageContext) arg));
            }
        });
    }

    public void startFactory() throws DeveloperBugException, InvalidConfigException, InterruptedException, IOException {
        logger.info("Factory has started!");
        initFXMLView();
        factory.start();
    }

    public void initFXMLView() {
        Platform.runLater(() -> {
            fxmlViewController.setCountOfAccessoryProducers(configParser.getAccessorySupplCount());
            fxmlViewController.setCountOfDealers(configParser.getDealersCount());
            fxmlViewController.setCountOfWorkers(configParser.getWorkersCount());
        });
    }
}
