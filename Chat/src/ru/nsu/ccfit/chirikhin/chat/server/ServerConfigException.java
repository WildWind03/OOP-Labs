package ru.nsu.ccfit.chirikhin.chat.server;

import org.apache.log4j.Logger;

public class ServerConfigException extends Exception {
    private static final Logger logger = Logger.getLogger(ServerConfigException.class.getName());

    public ServerConfigException(String message) {
        super(message);
    }
}
