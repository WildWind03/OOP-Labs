package ru.nsu.ccfit.chirikhin.chat.service;

import org.apache.log4j.Logger;
import ru.nsu.ccfit.chirikhin.chat.server.ServerMessageController;

public class CommandText implements ClientMessage {
    private static final Logger logger = Logger.getLogger(CommandText.class.getName());
    private final String messageType = "message";
    private final String message;
    private final String session;

    public CommandText(String message, String session) {
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
