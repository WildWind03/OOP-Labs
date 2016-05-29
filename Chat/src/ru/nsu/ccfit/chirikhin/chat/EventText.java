package ru.nsu.ccfit.chirikhin.chat;

import org.apache.log4j.Logger;

public class EventText implements ServerMessage {
    private static final Logger logger = Logger.getLogger(EventText.class.getName());

    private final String message;
    private final String type;

    public EventText(String type, String message) {
        this.type = type;
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public void process(ClientMessageController clientMessageController) {
        clientMessageController.handleTextMessage(this);
    }
}
