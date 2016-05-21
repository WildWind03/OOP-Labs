package ru.nsu.ccfit.chirikhin.chat;

import org.apache.log4j.Logger;

public class InvalidConfigException extends Exception {
    private static final Logger logger = Logger.getLogger(InvalidConfigException.class.getName());

    public InvalidConfigException() {
    }

    public InvalidConfigException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidConfigException(String message) {
        super(message);
    }

    public InvalidConfigException(Throwable cause) {
        super(cause);
    }

    public InvalidConfigException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
