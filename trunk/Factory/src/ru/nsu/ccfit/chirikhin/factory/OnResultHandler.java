package ru.nsu.ccfit.chirikhin.factory;

import org.apache.log4j.Logger;

public class OnResultHandler {
    private Logger logger = Logger.getLogger(OnResultHandler.class.getName());

    public void handle() {
        logger.info("The task is completed!");
    }
}
