package ru.nsu.ccfit.chirikhin.chat;

import org.apache.log4j.Logger;

public class CommandText implements ClientMessage {
    private static final Logger logger = Logger.getLogger(CommandText.class.getName());
    private final String messageType = "message";
    private final String message;
    private final long session;

    public CommandText(String message, long session) {
        this.message = message;
        this.session = session;
    }

    @Override
    public void process(ServerMessageController serverMessageController) {
        if (null == serverMessageController) {
            throw new NullPointerException("Null reference instead of Message Controller");
        }

        logger.info("Process");
        serverMessageController.handleTextMessage(this, session);
    }

    public String getMessage() {
        return message;
    }
}
