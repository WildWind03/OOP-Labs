package ru.nsu.ccfit.chirikhin.chat.server;

import org.apache.log4j.Logger;

public class NicknameBusyException extends Exception {
    private static final Logger logger = Logger.getLogger(NicknameBusyException.class.getName());

    public NicknameBusyException() {
        super();
    }

    public NicknameBusyException(String message) {
        super(message);
    }

    public NicknameBusyException(String message, Throwable cause) {
        super(message, cause);
    }

    public NicknameBusyException(Throwable cause) {
        super(cause);
    }

    protected NicknameBusyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
