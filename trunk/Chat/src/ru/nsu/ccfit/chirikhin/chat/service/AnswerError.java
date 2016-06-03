package ru.nsu.ccfit.chirikhin.chat.service;

import org.apache.log4j.Logger;
import ru.nsu.ccfit.chirikhin.chat.client.model.ClientMessageController;
import ru.nsu.ccfit.chirikhin.chat.client.model.ClientMessageControllerFunctionalityException;

public class AnswerError implements ServerMessage {
    private static final Logger logger = Logger.getLogger(AnswerError.class.getName());

    private final String reason;

    public AnswerError(String reason) {
        if (null == reason) {
            throw new NullPointerException("Reason can not be null");
        }
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }

    @Override
    public void process(ClientMessageController clientMessageController) {
        if (null == clientMessageController) {
            throw new NullPointerException("Null clientMessageController");
        }

        try {
            clientMessageController.handleErrorServerMessage(this);
        } catch (ClientMessageControllerFunctionalityException e) {
            logger.error("Error while processing");
        }
    }
}
