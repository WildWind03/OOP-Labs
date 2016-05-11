package ru.nsu.ccfit.chirikhin.chat.server;

import org.apache.log4j.Logger;

import java.util.concurrent.BlockingQueue;

public class MessageController implements Runnable {
    private static final Logger logger = Logger.getLogger(MessageController.class.getName());

    private final BlockingQueue<UserMessageStore> userMessageStores;

    public MessageController(BlockingQueue<UserMessageStore> userMessageStores) {
        this.userMessageStores = userMessageStores;
    }

    public void takeMessage(Message message) {
        message.process(this);
    }

    public BlockingQueue<UserMessageStore> getUserMessageStores() {
        return userMessageStores;
    }

    @Override
    public void run() {

    }
}
