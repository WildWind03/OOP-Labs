package ru.nsu.ccfit.chirikhin.chat.service;

import org.apache.log4j.Logger;
import ru.nsu.ccfit.chirikhin.chat.client.model.ClientMessageController;

public class EventConnectionFailed implements ServerMessage {
    private static final Logger logger = Logger.getLogger(EventConnectionFailed.class.getName());

    @Override
    public void process(ClientMessageController clientMessageController) {
        if (null == clientMessageController) {
            throw new NullPointerException("Null clientMessageController");
        }
        clientMessageController.handleEventConnectionFailed(this);
    }
}
