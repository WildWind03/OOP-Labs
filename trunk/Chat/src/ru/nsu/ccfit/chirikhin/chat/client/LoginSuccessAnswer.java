package ru.nsu.ccfit.chirikhin.chat.client;

import org.apache.log4j.Logger;

public class LoginSuccessAnswer implements ServerEvent{
    private static final Logger logger = Logger.getLogger(LoginSuccessAnswer.class.getName());
    private final String sessionId;

    public LoginSuccessAnswer(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getSessionId() {
        return sessionId;
    }

    @Override
    public void process(ClientViewController clientViewController) {
        clientViewController.onLoginSuccessAnswer(this);
    }
}
