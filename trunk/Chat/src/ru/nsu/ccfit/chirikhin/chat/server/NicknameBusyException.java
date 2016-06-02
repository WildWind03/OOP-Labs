package ru.nsu.ccfit.chirikhin.chat.server;

import org.apache.log4j.Logger;

public class NicknameBusyException extends Exception {
    private static final Logger logger = Logger.getLogger(NicknameBusyException.class.getName());

    public NicknameBusyException(String message) {
        super(message);
    }
}
