package ru.nsu.ccfit.chirikhin.chat.client;

import org.apache.log4j.Logger;

public class LogoutFailedAnswer implements ServerEvent{
    private static final Logger logger = Logger.getLogger(LogoutFailedAnswer.class.getName());
    private final String reason;

    public LogoutFailedAnswer(String reason) {
        if (null == reason) {
            throw new NullPointerException("reason can not be null");
        }

        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }

    @Override
    public void process(ClientViewController clientViewController) {
        if (null == clientViewController) {
            throw new NullPointerException("ClientViewController is null");
        }

        clientViewController.onLogoutFailedAnswer(this);
    }
}
