package ru.nsu.ccfit.chirikhin.chat.client;

import org.apache.log4j.Logger;

public class ConnectionFailedException extends Exception {
    private static final Logger logger = Logger.getLogger(ConnectionFailedException.class.getName());

    public ConnectionFailedException(String message) {
        super(message);
    }
}
