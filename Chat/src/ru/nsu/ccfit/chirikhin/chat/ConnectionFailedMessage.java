package ru.nsu.ccfit.chirikhin.chat;

import org.apache.log4j.Logger;

public class ConnectionFailedMessage implements ServerMessage {
    private static final Logger logger = Logger.getLogger(ConnectionFailedMessage.class.getName());

    @Override
    public void process(ClientMessageController clientMessageController) {

    }
}
