package ru.nsu.ccfit.chirikhin.chat.server;

import org.apache.log4j.Logger;

public class SocketNotOpenedException extends Exception {
    private static final Logger logger = Logger.getLogger(SocketNotOpenedException.class.getName());

    public SocketNotOpenedException() {
    }

    public SocketNotOpenedException(String message) {
        super(message);
    }

    public SocketNotOpenedException(String message, Throwable cause) {
        super(message, cause);
    }

    public SocketNotOpenedException(Throwable cause) {
        super(cause);
    }

    public SocketNotOpenedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
