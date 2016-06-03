package ru.nsu.ccfit.chirikhin.chat.service;

import org.apache.log4j.Logger;
import ru.nsu.ccfit.chirikhin.chat.client.model.ClientMessageController;
import ru.nsu.ccfit.chirikhin.chat.client.model.ClientMessageControllerFunctionalityException;

public class AnswerSuccess implements ServerMessage {
    private static final Logger logger = Logger.getLogger(AnswerSuccess.class.getName());

    @Override
    public void process(ClientMessageController clientMessageController) {
        if (null == clientMessageController) {
            throw new NullPointerException("Null clientMessageController");
        }

        try {
            clientMessageController.handleSuccessServerAnswer(this);
        } catch (ClientMessageControllerFunctionalityException e) {
            logger.error("Exception while processing");
        }
    }
}
