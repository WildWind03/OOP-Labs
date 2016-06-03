package ru.nsu.ccfit.chirikhin.chat.service;

import org.apache.log4j.Logger;
import ru.nsu.ccfit.chirikhin.chat.client.model.ClientMessageController;

public class EventText implements ServerMessage {
    private static final Logger logger = Logger.getLogger(EventText.class.getName());
    private final String messageType = "message";

    private final String message;
    private final String name;

    public EventText(String name, String message) {
        if (null == name || null == message) {
            throw new NullPointerException("name and message can not be null");
        }
        this.name = name;
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public void process(ClientMessageController clientMessageController) {
        if (null == clientMessageController) {
            throw new NullPointerException("Null clientMessageController");
        }

        clientMessageController.handleTextMessage(this);
    }
}
