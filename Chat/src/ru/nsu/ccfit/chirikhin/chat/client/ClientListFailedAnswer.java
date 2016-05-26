package ru.nsu.ccfit.chirikhin.chat.client;

import org.apache.log4j.Logger;

public class ClientListFailedAnswer implements ServerEvent{
    private static final Logger logger = Logger.getLogger(ClientListFailedAnswer.class.getName());

    private final String reason;
    public ClientListFailedAnswer(String reason) {
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }

    @Override
    public void process(ClientViewController clientViewController) {
        clientViewController.onClientListFailedAnswer(this);
    }
}
