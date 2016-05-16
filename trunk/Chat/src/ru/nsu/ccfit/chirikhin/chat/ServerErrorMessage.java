package ru.nsu.ccfit.chirikhin.chat;

import org.apache.log4j.Logger;
import ru.nsu.ccfit.chirikhin.chat.client.ClientMessageController;
import ru.nsu.ccfit.chirikhin.chat.server.ServerMessageController;

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
    public void process(ClientMessageController clientrMessageController) {

    }
}
