package ru.nsu.ccfit.chirikhin.chat.client;

import org.apache.log4j.Logger;

public class ConnectionFailedEvent implements ServerEvent {
    private static final Logger logger = Logger.getLogger(ConnectionFailedEvent.class.getName());

    @Override
    public void process(ClientViewController clientViewController) {
        if (null == clientViewController) {
            throw new NullPointerException("client View Controller is null");
        }

        clientViewController.onConnectionFailedEvent(this);
    }
}
