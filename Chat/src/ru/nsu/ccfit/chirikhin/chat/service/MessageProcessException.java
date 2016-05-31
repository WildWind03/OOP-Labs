package ru.nsu.ccfit.chirikhin.chat.service;

import org.apache.log4j.Logger;

public class MessageProcessException extends Exception {
    private static final Logger logger = Logger.getLogger(MessageProcessException.class.getName());

    public MessageProcessException(String message) {
        super(message);
    }
}
