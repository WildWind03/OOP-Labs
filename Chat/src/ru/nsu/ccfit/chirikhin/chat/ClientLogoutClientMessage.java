package ru.nsu.ccfit.chirikhin.chat;

import org.apache.log4j.Logger;

public class ClientLogoutClientMessage implements ClientMessage {
    private static final Logger logger = Logger.getLogger(ClientLogoutClientMessage.class.getName());
    private final long sessionId;

    public long getSessionId() {
        return sessionId;
    }

    public ClientLogoutClientMessage(long sessionId) {
        this.sessionId = sessionId;
    }

    @Override
    public void process(ServerMessageController messageController) throws MessageProcessException {
        messageController.handleExitMessage(this, getSessionId());
    }
}
