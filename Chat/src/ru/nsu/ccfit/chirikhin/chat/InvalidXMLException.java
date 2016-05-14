package ru.nsu.ccfit.chirikhin.chat;

import org.apache.log4j.Logger;

public class InvalidXMLException extends Exception {
    public InvalidXMLException() {
    }

    public InvalidXMLException(String message) {
        super(message);
    }

    public InvalidXMLException(Throwable cause) {
        super(cause);
    }

    public InvalidXMLException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidXMLException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    private static final Logger logger = Logger.getLogger(InvalidXMLException.class.getName());
}
