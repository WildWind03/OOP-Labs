package ru.nsu.ccfit.chirikhin.factory;/**
 * Created by wild_wind on 16.04.16.
 */

import org.apache.log4j.Logger;

public class FactoryController {
    private Logger logger = Logger.getLogger(FactoryController.class.getName());
    private final String pathToFile;
    private final Factory factory;
    private final SwingView view;

    public FactoryController(String pathToFile) {
        logger.info("------------------------");
        this.pathToFile = pathToFile;
        factory = new Factory(pathToFile);
        view = new SwingView();
        factory.addObserver(view);
    }

    public void startFactory() {
        logger.info("Factory has started!");
        factory.start();
    }
}
