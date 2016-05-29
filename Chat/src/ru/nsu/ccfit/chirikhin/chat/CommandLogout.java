package ru.nsu.ccfit.chirikhin.chat;

import org.apache.log4j.Logger;

public class CommandLogout implements ClientMessage {
    private static final Logger logger = Logger.getLogger(CommandLogout.class.getName());
    private final String messageType = "logout";

    private final long session;

    public long getSession() {
        return session;
    }

    public CommandLogout(long session) {
        this.session = session;
    }

    @Override
    public void process(ServerMessageController messageController) throws MessageProcessException {
        messageController.handleExitMessage(this, getSession());
    }
}
