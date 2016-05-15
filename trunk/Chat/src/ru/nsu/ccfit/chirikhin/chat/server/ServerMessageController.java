package ru.nsu.ccfit.chirikhin.chat.server;

import org.apache.log4j.Logger;
import ru.nsu.ccfit.chirikhin.autoqueue.Autoqueue;
import ru.nsu.ccfit.chirikhin.chat.ClientMessage;
import ru.nsu.ccfit.chirikhin.chat.Message;
import ru.nsu.ccfit.chirikhin.chat.MessageController;

import java.util.concurrent.BlockingQueue;

public class ServerMessageController implements MessageController {
    private static final Logger logger = Logger.getLogger(ServerMessageController.class.getName());

    private final BlockingQueue<Client> clients;
    private final Autoqueue<Message> messages;

    public ServerMessageController(Autoqueue<Message> messages, BlockingQueue<Client> clients) {
        if (null == clients || null == messages) {
            throw new NullPointerException("Null reference in constructor");
        }

        this.clients = clients;
        this.messages = messages;
    }

    public BlockingQueue<Client> getClients() {
        return clients;
    }

    @Override
    public void acceptMessage(Message message)  {
        logger.info("Message has been taken by controller");
        message.process(this);

        if (message instanceof ClientMessage) {
            try {
                messages.put(message);
            } catch (InterruptedException e) {
                logger.error("Interrupt exception");
            }
        }
    }
}
