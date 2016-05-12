package ru.nsu.ccfit.chirikhin.chat.server;

import org.apache.log4j.Logger;

import java.util.concurrent.BlockingQueue;

public class MessageController implements Runnable {
    private static final Logger logger = Logger.getLogger(MessageController.class.getName());

    private final BlockingQueue<Client> clients;
    private final BlockingQueue<Message> messages;

    public MessageController(BlockingQueue<Message> messages, BlockingQueue<Client> clients) {
        this.messages = messages;
        this.clients = clients;
    }

    @Override
    public void run() {

    }
}
