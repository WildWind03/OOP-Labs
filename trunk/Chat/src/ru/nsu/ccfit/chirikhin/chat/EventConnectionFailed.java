package ru.nsu.ccfit.chirikhin.chat;

import org.apache.log4j.Logger;

public class EventConnectionFailed implements ServerMessage {
    private static final Logger logger = Logger.getLogger(EventConnectionFailed.class.getName());

    @Override
    public void process(ClientMessageController clientMessageController) {

    }
}
