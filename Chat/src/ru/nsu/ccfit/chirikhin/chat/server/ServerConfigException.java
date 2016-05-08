package ru.nsu.ccfit.chirikhin.chat.server;

import org.apache.log4j.Logger;

public class ServerConfigException extends Exception {
    public ServerConfigException() {
    }

    public ServerConfigException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServerConfigException(String message) {
        super(message);
    }

    public ServerConfigException(Throwable cause) {
        super(cause);
    }

    public ServerConfigException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    private static final Logger logger = Logger.getLogger(ServerConfigException.class.getName());
}
