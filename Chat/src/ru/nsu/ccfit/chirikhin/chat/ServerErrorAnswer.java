package ru.nsu.ccfit.chirikhin.chat;

import org.apache.log4j.Logger;

public class ServerErrorAnswer implements ServerMessage {
    private static final Logger logger = Logger.getLogger(ServerErrorAnswer.class.getName());

    private final String errorReason;

    public ServerErrorAnswer(String errorReason) {
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
