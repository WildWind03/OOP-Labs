package ru.nsu.ccfit.chirikhin.chat;

import org.apache.log4j.Logger;

public class TimeoutException extends Exception {
    private static final Logger logger = Logger.getLogger(TimeoutException.class.getName());

    public TimeoutException() {
    }

    public TimeoutException(String message) {
        super(message);
    }

    public TimeoutException(String message, Throwable cause) {
        super(message, cause);
    }

    public TimeoutException(Throwable cause) {
        super(cause);
    }

    public TimeoutException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
