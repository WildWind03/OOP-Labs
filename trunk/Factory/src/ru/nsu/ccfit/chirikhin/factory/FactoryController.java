package ru.nsu.ccfit.chirikhin.factory;/**
 * Created by wild_wind on 16.04.16.
 */

import javafx.application.Platform;
import org.apache.log4j.Logger;

import java.io.IOException;

public class FactoryController {
    private Logger logger = Logger.getLogger(FactoryController.class.getName());
    private final String pathToFile;
    private final Factory factory;
    private final JavaFXView view;

    public FactoryController(String pathToFile) throws InvalidConfigException, IOException, DeveloperBugException {
        logger.info("------------------------ Factory Controller has started!");
        this.pathToFile = pathToFile;
        factory = new Factory(pathToFile);
        JavaFXView.initView();
        view = new JavaFXView();
        factory.setOnEngineStorageChangedHandler(new OnEngineStorageChangedHandler(view));
    }

    public void startFactory() throws DeveloperBugException, InvalidConfigException, InterruptedException, IOException {
        logger.info("Factory has started!");
        factory.start();
    }
}
