package ru.nsu.ccfit.chirikhin.chat.client;

import org.apache.log4j.Logger;

public class NewMessageEvent implements ServerEvent {
    private static final Logger logger = Logger.getLogger(NewMessageEvent.class.getName());
    private final String message;
    private final String clientType;

    public String getMessage() {
        return message;
    }

    public String getClientType() {
        return clientType;
    }

    public NewMessageEvent(String message, String clientType) {

        this.message = message;
        this.clientType = clientType;
    }

    @Override
    public void process(ClientViewController clientViewController) {
        clientViewController.onNewMessageEvent(this);
    }
}
