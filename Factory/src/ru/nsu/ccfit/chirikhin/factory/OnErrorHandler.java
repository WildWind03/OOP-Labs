package ru.nsu.ccfit.chirikhin.factory;

import org.apache.log4j.Logger;

public class OnErrorHandler {
    private Logger logger = Logger.getLogger(OnErrorHandler.class.getName());

    public void handle(Exception e) {
        logger.fatal(e.toString());
    }
}
