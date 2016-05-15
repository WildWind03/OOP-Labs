package ru.nsu.ccfit.chirikhin.chat.server;

import org.apache.log4j.Logger;
import ru.nsu.ccfit.chirikhin.chat.MessageController;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;

public class ClientCreator {
    private static final Logger logger = Logger.getLogger(ClientCreator.class.getName());

    private final BlockingQueue<Client> clients;

    private final MessageController messageController;

    public ClientCreator(BlockingQueue<Client> clients, MessageController messageController) {
        if (null == clients || null == messageController) {
            throw new NullPointerException("Null reference");
        }

        this.messageController = messageController;
        this.clients = clients;
    }

    public void createClient(SocketDescriptor socketDescriptor) throws IOException, ParserConfigurationException {
        if (null == socketDescriptor) {
            throw new NullPointerException("Null instead of socketDescriptor");
        }

        Client client = new Client(socketDescriptor.getSocket(), socketDescriptor.getProtocolName(), messageController);
        clients.add(client);
    }
}
