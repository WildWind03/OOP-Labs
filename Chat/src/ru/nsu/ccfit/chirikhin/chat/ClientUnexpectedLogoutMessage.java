package ru.nsu.ccfit.chirikhin.chat;

import org.apache.log4j.Logger;

public class ClientUnexpectedLogoutMessage implements ClientMessage{
    private static final Logger logger = Logger.getLogger(ClientUnexpectedLogoutMessage.class.getName());

    private final long sessionId;

    public ClientUnexpectedLogoutMessage(long sessionId) {
        this.sessionId = sessionId;
    }

    @Override
    public void process(ServerMessageController messageController) throws MessageProcessException {
        messageController.handleConnectionFailedMessage(this, sessionId);
    }
}
