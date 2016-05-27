package ru.nsu.ccfit.chirikhin.chat;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.Collections;
import java.util.List;

public class LoggerController {
    private static final Logger logger = Logger.getLogger(LoggerController.class.getName());
    private LoggerController() {

    }

    public static void switchOffLogger() {
        List<Logger> loggers = Collections.<Logger>list(LogManager.getCurrentLoggers());
        loggers.add(LogManager.getRootLogger());
        for (Logger logger : loggers) {
            logger.setLevel(Level.OFF);
        }
    }
}
