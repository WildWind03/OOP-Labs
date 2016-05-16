package ru.nsu.ccfit.chirikhin.chat;

import org.apache.log4j.Logger;
import ru.nsu.ccfit.chirikhin.chat.client.ClientMessageController;

public class ServerTextMessage implements ServerMessage {
    private static final Logger logger = Logger.getLogger(ServerTextMessage.class.getName());

    private final String author;
    private final String text;

    public ServerTextMessage(String author, String text) {
        this.author = author;
        this.text = text;
    }

    public String getAuthor() {
        return author;
    }

    public String getText() {
        return text;
    }

    @Override
    public void process(ClientMessageController clientMessageController) {
        clientMessageController.handleTextMessage(this);
    }
}
