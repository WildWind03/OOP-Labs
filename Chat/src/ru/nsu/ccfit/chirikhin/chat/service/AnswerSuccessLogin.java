package ru.nsu.ccfit.chirikhin.chat.service;

import org.apache.log4j.Logger;
import ru.nsu.ccfit.chirikhin.chat.client.model.ClientMessageController;

public class AnswerSuccessLogin implements ServerMessage {
    private static final Logger logger = Logger.getLogger(AnswerSuccessLogin.class.getName());
    private final String session;

    public AnswerSuccessLogin(String session) {
        if (null == session) {
            throw new NullPointerException("Session can not be null");
        }
        this.session = session;
    }

    @Override
    public void process(ClientMessageController clientMessageController) {
        if (null == clientMessageController) {
            throw new NullPointerException("Null clientMessageController");
        }

        clientMessageController.handleSuccessLoginServerMessage(this);
    }

    public String getSession() {
        return session;
    }
}
