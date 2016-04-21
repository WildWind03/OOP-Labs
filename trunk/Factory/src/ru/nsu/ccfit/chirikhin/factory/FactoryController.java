package ru.nsu.ccfit.chirikhin.factory;/**
 * Created by wild_wind on 16.04.16.
 */

import javafx.stage.Stage;
import org.apache.log4j.Logger;

import java.io.IOException;

public class FactoryController {
    private Logger logger = Logger.getLogger(FactoryController.class.getName());
    private final String pathToFile;
    private final Factory factory;
    private final JavaFXView view;

    public FactoryController(String pathToFile) {
        logger.info("------------------------");
        this.pathToFile = pathToFile;
        factory = new Factory(pathToFile, view);
        JavaFXView.initView();
        view = new JavaFXView();
    }

    public void startFactory() throws DeveloperBugException, InvalidConfigException, InterruptedException, IOException {
        logger.info("Factory has started!");
        factory.start();
    }
}
