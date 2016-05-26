package ru.nsu.ccfit.chirikhin.chat;

import org.apache.log4j.Logger;

public class ServerTextMessage implements ServerMessage {
    private static final Logger logger = Logger.getLogger(ServerTextMessage.class.getName());

    //private final String author;
    private final String text;
    private final String clientType;

    public ServerTextMessage(String clientType, String text) {
        //this.author = author;
        this.clientType = clientType;
        this.text = text;
    }

    //public String getAuthor() {
        //return author;
    //}

    public String getClientType() {
        return clientType;
    }

    public String getText() {
        return text;
    }

    @Override
    public void process(ClientMessageController clientMessageController) {
        clientMessageController.handleTextMessage(this);
    }
}
