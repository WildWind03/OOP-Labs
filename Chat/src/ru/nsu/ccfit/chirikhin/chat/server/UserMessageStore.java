package ru.nsu.ccfit.chirikhin.chat.server;

import org.apache.log4j.Logger;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class UserMessageStore {
    private static final Logger logger = Logger.getLogger(UserMessageStore.class.getName());
    private final BlockingQueue<Message> messages = new LinkedBlockingQueue<>();

    public UserMessageStore() {

    }

    public void addMessage(Message message) {
        messages.add(message);
    }

    public Message takeMessage() throws InterruptedException {
        return messages.take();
    }
}
