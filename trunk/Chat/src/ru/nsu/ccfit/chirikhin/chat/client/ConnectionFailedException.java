package ru.nsu.ccfit.chirikhin.chat.client;

import org.apache.log4j.Logger;

public class ConnectionFailedException extends Exception {
    private static final Logger logger = Logger.getLogger(ConnectionFailedException.class.getName());

    public ConnectionFailedException() {
    }

    public ConnectionFailedException(String message) {
        super(message);
    }

    public ConnectionFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConnectionFailedException(Throwable cause) {
        super(cause);
    }

    public ConnectionFailedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
