package ru.nsu.ccfit.chirikhin.chat.client;

import org.apache.log4j.Logger;

import java.util.stream.Stream;

public class LoginFailedAnswer implements ServerEvent{
    private static final Logger logger = Logger.getLogger(LoginFailedAnswer.class.getName());

    private final String reason;

    public LoginFailedAnswer(String reason) {
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }

    @Override
    public void process(ClientViewController clientViewController) {
        clientViewController.onLoginFailedAnswer(this);
    }
}
