package ru.nsu.ccfit.chirikhin.chat.server;

import org.apache.log4j.Logger;

import java.util.concurrent.BlockingQueue;

public class MessageController {
    private static final Logger logger = Logger.getLogger(MessageController.class.getName());

    public void takeMessage(Message message) {
        message.process(this);
    }
}
