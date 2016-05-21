package ru.nsu.ccfit.chirikhin.chat;

import org.apache.log4j.Logger;

public class ServerSuccessAnswer implements ServerMessage {
    private static final Logger logger = Logger.getLogger(ServerSuccessAnswer.class.getName());

    @Override
    public void process(ClientMessageController clientMessageController) {
        clientMessageController.handleSuccessServerAnswer(this);
    }
}
