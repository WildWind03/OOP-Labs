package ru.nsu.ccfit.chirikhin.chat.client;

import org.apache.log4j.Logger;

public class MessageDeliveredAnswer implements ServerEvent {
    private static final Logger logger = Logger.getLogger(MessageDeliveredAnswer.class.getName());

    @Override
    public void process(ClientViewController clientViewController) {
        clientViewController.onMessageDeliveredAnswer(this);
    }
}
