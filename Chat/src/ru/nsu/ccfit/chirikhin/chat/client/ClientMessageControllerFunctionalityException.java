package ru.nsu.ccfit.chirikhin.chat.client;

import org.apache.log4j.Logger;

public class ClientMessageControllerFunctionalityException extends Exception{
    public ClientMessageControllerFunctionalityException() {
        super();
    }

    public ClientMessageControllerFunctionalityException(String message) {
        super(message);
    }

    public ClientMessageControllerFunctionalityException(String message, Throwable cause) {
        super(message, cause);
    }

    public ClientMessageControllerFunctionalityException(Throwable cause) {
        super(cause);
    }

    protected ClientMessageControllerFunctionalityException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    private static final Logger logger = Logger.getLogger(ClientMessageControllerFunctionalityException.class.getName());
}
