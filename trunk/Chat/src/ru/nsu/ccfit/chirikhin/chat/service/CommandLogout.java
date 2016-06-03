package ru.nsu.ccfit.chirikhin.chat.service;

import org.apache.log4j.Logger;
import ru.nsu.ccfit.chirikhin.chat.server.ServerMessageController;

public class CommandLogout implements ClientMessage {
    private static final Logger logger = Logger.getLogger(CommandLogout.class.getName());
    private final String messageType = "logout";

    private final String session;

    public String getSession() {
        return session;
    }

    public CommandLogout(String session) {
        if (null == session) {
            throw new NullPointerException("Session can not be null");
        }

        this.session = session;
    }

    @Override
    public void process(ServerMessageController messageController) throws MessageProcessException {
        if (null == messageController) {
            throw new NullPointerException("Null serverMessageController");
        }

        messageController.handleExitMessage(this, getSession());
    }
}
