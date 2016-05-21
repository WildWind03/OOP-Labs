package ru.nsu.ccfit.chirikhin.chat;

import org.apache.log4j.Logger;

public class ClientListMessage implements ClientMessage {
    private static final Logger logger = Logger.getLogger(ClientListMessage.class.getName());
    private final long sessionId;

    public long getSessionId() {
        return sessionId;
    }

    public ClientListMessage(long sessionId) {

        this.sessionId = sessionId;
    }

    @Override
    public void process(ServerMessageController serverMessageController) {
        serverMessageController.handleClientListMessage(this, sessionId);
    }
}
