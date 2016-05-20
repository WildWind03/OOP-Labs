package ru.nsu.ccfit.chirikhin.chat;

import org.apache.log4j.Logger;

public class ServerErrorMessage implements ServerMessage {
    private static final Logger logger = Logger.getLogger(ServerErrorMessage.class.getName());

    private final String errorReason;

    public ServerErrorMessage(String errorReason) {
        this.errorReason = errorReason;
    }

    public String getErrorReason() {
        return errorReason;
    }

    @Override
    public void process(ClientMessageController clientMessageController) {
        clientMessageController.handleErrorServerMessage(this);
    }
}
