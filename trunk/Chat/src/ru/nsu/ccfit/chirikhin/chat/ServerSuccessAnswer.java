package ru.nsu.ccfit.chirikhin.chat;

import org.apache.log4j.Logger;
import ru.nsu.ccfit.chirikhin.chat.client.ClientMessageControllerFunctionalityException;

public class ServerSuccessAnswer implements ServerMessage {
    private static final Logger logger = Logger.getLogger(ServerSuccessAnswer.class.getName());

    @Override
    public void process(ClientMessageController clientMessageController) {
        try {
            clientMessageController.handleSuccessServerAnswer(this);
        } catch (ClientMessageControllerFunctionalityException e) {
            logger.error("Exception while processing");
        }
    }
}
