package ru.nsu.ccfit.chirikhin.chat;

import org.apache.log4j.Logger;
import ru.nsu.ccfit.chirikhin.chat.client.ClientMessageControllerFunctionalityException;

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
        try {
            clientMessageController.handleErrorServerMessage(this);
        } catch (ClientMessageControllerFunctionalityException e) {
            logger.error("Error while processing");
        }
    }
}
