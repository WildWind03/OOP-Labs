package ru.nsu.ccfit.chirikhin.factory;/**
 * Created by wild_wind on 16.04.16.
 */

import javafx.application.Platform;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Observable;

public class FactoryController {
    private Logger logger = Logger.getLogger(FactoryController.class.getName());
    private final String pathToFile;
    private final Factory factory;

    public FactoryController(String pathToFile, FXMLViewController fxmlViewController) throws InvalidConfigException, IOException, DeveloperBugException{
        logger.info("------------------------ Factory Controller has started!");
        this.pathToFile = pathToFile;
        factory = new Factory(pathToFile);
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
        factory.start();
    }
}
