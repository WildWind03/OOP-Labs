package ru.nsu.ccfit.chirikhin.chat.client.model;

import org.apache.log4j.Logger;
import ru.nsu.ccfit.chirikhin.chat.client.view.ClientViewController;

public class MessageDeliveredAnswer implements ServerEvent {
    private static final Logger logger = Logger.getLogger(MessageDeliveredAnswer.class.getName());

    @Override
    public void process(ClientViewController clientViewController) {
        if (null == clientViewController) {
            throw new NullPointerException("ClientViewController is null");
        }

        clientViewController.onMessageDeliveredAnswer(this);
    }
}
