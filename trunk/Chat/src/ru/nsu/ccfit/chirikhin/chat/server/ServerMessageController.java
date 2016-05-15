package ru.nsu.ccfit.chirikhin.chat.server;

import org.apache.log4j.Logger;
import ru.nsu.ccfit.chirikhin.chat.Message;

import java.util.concurrent.BlockingQueue;

public class ServerMessageController implements Runnable {
    private static final Logger logger = Logger.getLogger(ServerMessageController.class.getName());

    private final BlockingQueue<Client> clients;
    private final BlockingQueue<Message> messages;

    public ServerMessageController(BlockingQueue<Message> messages, BlockingQueue<Client> clients) {
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
                logger.info("Message has been taken by controller");
                message.process(this);
            }
        } catch (InterruptedException e) {
            logger.error("Interrupt");
        }
    }
}
