package ru.nsu.ccfit.chirikhin.chat;

import org.apache.log4j.Logger;

public class ServerSuccessMessage implements ServerMessage {
    private static final Logger logger = Logger.getLogger(ServerSuccessMessage.class.getName());
    private final long sessionId;

    public ServerSuccessMessage(long sessionId) {
        this.sessionId = sessionId;
    }

    @Override
    public void process(ClientMessageController clientMessageController) {
        clientMessageController.handleSuccessServerMessage(this);
    }

    public long getSessionId() {
        return sessionId;
    }
}
