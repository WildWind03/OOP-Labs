package ru.nsu.ccfit.chirikhin.chat.service;

import org.apache.log4j.Logger;
import ru.nsu.ccfit.chirikhin.chat.client.ClientMessageController;

public class AnswerSuccessLogin implements ServerMessage {
    private static final Logger logger = Logger.getLogger(AnswerSuccessLogin.class.getName());
    private final String session;

    public AnswerSuccessLogin(String session) {
        this.session = session;
    }

    @Override
    public void process(ClientMessageController clientMessageController) {
        clientMessageController.handleSuccessLoginServerMessage(this);
    }

    public String getSession() {
        return session;
    }
}
