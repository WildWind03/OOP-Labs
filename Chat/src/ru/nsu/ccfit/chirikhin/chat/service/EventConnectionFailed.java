package ru.nsu.ccfit.chirikhin.chat.service;

import org.apache.log4j.Logger;
import ru.nsu.ccfit.chirikhin.chat.client.ClientMessageController;

public class EventConnectionFailed implements ServerMessage {
    private static final Logger logger = Logger.getLogger(EventConnectionFailed.class.getName());

    @Override
    public void process(ClientMessageController clientMessageController) {
        clientMessageController.handleEventConnectionFailed(this);
    }
}
