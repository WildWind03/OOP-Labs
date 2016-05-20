package ru.nsu.ccfit.chirikhin.chat;

import org.apache.log4j.Logger;

public class SignedMessage {
    private static final Logger logger = Logger.getLogger(SignedMessage.class.getName());

    private final ClientMessage clientMessage;
    private long sessionId;

    public SignedMessage(ClientMessage clientMessage, long sessionId) {
        this.clientMessage = clientMessage;
        this.sessionId = sessionId;
    }

    public ClientMessage getClientMessage() {
        return clientMessage;
    }

    public long getSessionId() {
        return sessionId;
    }
}
