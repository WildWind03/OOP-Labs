package ru.nsu.ccfit.chirikhin.chat;

import org.apache.log4j.Logger;

public class InvalidXMLException extends Exception {
    private static final Logger logger = Logger.getLogger(InvalidXMLException.class.getName());

    public InvalidXMLException(String message) {
        super(message);
    }
}
