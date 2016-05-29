package ru.nsu.ccfit.chirikhin.chat;

import org.apache.log4j.Logger;
import ru.nsu.ccfit.chirikhin.chat.client.ClientMessageControllerFunctionalityException;

public class AnswerError implements ServerMessage {
    private static final Logger logger = Logger.getLogger(AnswerError.class.getName());

    private final String reason;

    public AnswerError(String reason) {
        this.reason = reason;
    }

    public String getReason() {
        return reason;
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
