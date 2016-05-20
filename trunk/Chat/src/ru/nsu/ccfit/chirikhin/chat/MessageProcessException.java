package ru.nsu.ccfit.chirikhin.chat;

import org.apache.log4j.Logger;

public class MessageProcessException extends Exception {
    private static final Logger logger = Logger.getLogger(MessageProcessException.class.getName());

    public MessageProcessException() {
        super();
    }

    public MessageProcessException(String message) {
        super(message);
    }

    public MessageProcessException(String message, Throwable cause) {
        super(message, cause);
    }

    public MessageProcessException(Throwable cause) {
        super(cause);
    }

    protected MessageProcessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
