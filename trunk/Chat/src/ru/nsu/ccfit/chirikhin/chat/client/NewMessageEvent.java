package ru.nsu.ccfit.chirikhin.chat.client;

import org.apache.log4j.Logger;

public class NewMessageEvent implements ServerEvent {
    private static final Logger logger = Logger.getLogger(NewMessageEvent.class.getName());
    private final String message;
    private final String author;

    public String getMessage() {
        return message;
    }

    public String getAuthor() {
        return author;
    }

    public NewMessageEvent(String message, String clientType) {

        this.message = message;
        this.author = clientType;
    }

    @Override
    public void process(ClientViewController clientViewController) {
        clientViewController.onNewMessageEvent(this);
    }
}
