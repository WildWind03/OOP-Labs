package ru.nsu.ccfit.chirikhin.chat;

import org.apache.log4j.Logger;
import ru.nsu.ccfit.chirikhin.chat.server.Client;
import ru.nsu.ccfit.chirikhin.chat.server.MessageController;

import java.util.concurrent.BlockingQueue;

public class ClientMessage extends Message {
    private static final Logger logger = Logger.getLogger(ClientMessage.class.getName());
    private final String text;
    private final String author;

    public ClientMessage(String text, String author) {
        this.text = text;
        this.author = author;
    }

    @Override
    public void process(MessageController messageController) {
        if (null == messageController) {
            throw new NullPointerException("Null reference instead of Message Controller");
        }

        BlockingQueue<Client> clients = messageController.getClients();

        for(Client client : clients) {
            client.receiveMessage(this);
        }
    }

    public String getAuthor() {
        return author;
    }

    public String getText() {
        return text;
    }
}
