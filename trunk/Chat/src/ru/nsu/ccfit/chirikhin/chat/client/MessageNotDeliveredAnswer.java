package ru.nsu.ccfit.chirikhin.chat.client;

import org.apache.log4j.Logger;

public class MessageNotDeliveredAnswer implements ServerEvent{
    private static final Logger logger = Logger.getLogger(MessageNotDeliveredAnswer.class.getName());
    private final String reason;

    public String getReason() {
        return reason;
    }

    public MessageNotDeliveredAnswer(String reason) {

        this.reason = reason;
    }

    @Override
    public void process(ClientViewController clientViewController) {
        clientViewController.onMessageNotDeliveredAnswer(this);
    }
}
