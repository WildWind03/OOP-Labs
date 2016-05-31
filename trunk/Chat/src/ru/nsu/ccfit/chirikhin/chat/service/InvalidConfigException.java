package ru.nsu.ccfit.chirikhin.chat.service;

import org.apache.log4j.Logger;

public class InvalidConfigException extends Exception {
    private static final Logger logger = Logger.getLogger(InvalidConfigException.class.getName());

    public InvalidConfigException(String message) {
        super(message);
    }
}
