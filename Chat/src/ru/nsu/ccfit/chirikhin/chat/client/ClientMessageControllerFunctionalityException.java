package ru.nsu.ccfit.chirikhin.chat.client;

import org.apache.log4j.Logger;

public class ClientMessageControllerFunctionalityException extends Exception{

    public ClientMessageControllerFunctionalityException(String message) {
        super(message);
    }

    private static final Logger logger = Logger.getLogger(ClientMessageControllerFunctionalityException.class.getName());
}
