package ru.nsu.ccfit.chirikhin.chat.server;

import org.apache.log4j.Logger;

import java.util.concurrent.BlockingQueue;

public class ClientMessage extends Message {
    private static final Logger logger = Logger.getLogger(ClientMessage.class.getName());

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
}
