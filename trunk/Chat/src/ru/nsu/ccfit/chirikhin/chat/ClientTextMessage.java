package ru.nsu.ccfit.chirikhin.chat;

import org.apache.log4j.Logger;

public class ClientTextMessage implements ClientMessage {
    private static final Logger logger = Logger.getLogger(ClientTextMessage.class.getName());
    private final String text;
    private final long sessionID;

    public ClientTextMessage(String text, long sessionID) {
        this.text = text;
        this.sessionID = sessionID;
    }

    @Override
    public void process(ServerMessageController serverMessageController) {
        if (null == serverMessageController) {
            throw new NullPointerException("Null reference instead of Message Controller");
        }

        logger.info("Process");
        serverMessageController.handleTextMessage(this, sessionID);
    }

    public String getText() {
        return text;
    }
}
