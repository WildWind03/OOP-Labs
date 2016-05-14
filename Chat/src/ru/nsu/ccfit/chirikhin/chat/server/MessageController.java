package ru.nsu.ccfit.chirikhin.chat.server;

import org.apache.log4j.Logger;
import ru.nsu.ccfit.chirikhin.chat.Message;

import java.util.concurrent.BlockingQueue;

public class MessageController implements Runnable {
    private static final Logger logger = Logger.getLogger(MessageController.class.getName());

    private final BlockingQueue<Client> clients;
    private final BlockingQueue<Message> messages;

    public MessageController(BlockingQueue<Message> messages, BlockingQueue<Client> clients) {
        if (null == messages || null == clients) {
            throw new NullPointerException("Null reference in constructor");
        }

        this.messages = messages;
        this.clients = clients;
    }

    public BlockingQueue<Client> getClients() {
        return clients;
    }

    @Override
    public void run() {
        try {
            while(true) {
                Message message = messages.take();
                message.process(this);
            }
        } catch (InterruptedException e) {
            logger.error("Interrupt");
        }
    }
}
