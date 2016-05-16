package ru.nsu.ccfit.chirikhin.chat;

import org.apache.log4j.Logger;
import ru.nsu.ccfit.chirikhin.chat.server.ServerMessageController;

public class ClientTextMessage implements ClientMessage {
    private static final Logger logger = Logger.getLogger(ClientTextMessage.class.getName());
    private final String text;
    private final String author;

    public ClientTextMessage(String text, String author) {
        this.text = text;
        this.author = author;
    }

    @Override
    public void process(ServerMessageController serverMessageController) {
        if (null == serverMessageController) {
            throw new NullPointerException("Null reference instead of Message Controller");
        }

        logger.info("Process");
        serverMessageController.handleTextMessage(this);
    }

    public String getAuthor() {
        return author;
    }

    public String getText() {
        return text;
    }
}
