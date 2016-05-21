package ru.nsu.ccfit.chirikhin.chat;

import org.apache.log4j.Logger;

public class ServerSuccessLoginAnswer implements ServerMessage {
    private static final Logger logger = Logger.getLogger(ServerSuccessLoginAnswer.class.getName());
    private final long sessionId;

    public ServerSuccessLoginAnswer(long sessionId) {
        this.sessionId = sessionId;
    }

    @Override
    public void process(ClientMessageController clientMessageController) {
        clientMessageController.handleSuccessLoginServerMessage(this);
    }

    public long getSessionId() {
        return sessionId;
    }
}
