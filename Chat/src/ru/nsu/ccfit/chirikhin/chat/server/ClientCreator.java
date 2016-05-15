package ru.nsu.ccfit.chirikhin.chat.server;

import org.apache.log4j.Logger;
import ru.nsu.ccfit.chirikhin.autoqueue.Autoqueue;
import ru.nsu.ccfit.chirikhin.chat.Message;
import ru.nsu.ccfit.chirikhin.chat.MessageController;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;

public class ClientCreator {
    private static final Logger logger = Logger.getLogger(ClientCreator.class.getName());

    private final BlockingQueue<Client> clients;

    private final Autoqueue<Message> messages;

    public ClientCreator(BlockingQueue<Client> clients, Autoqueue<Message> messages) {
        if (null == clients || null == messages) {
            throw new NullPointerException("Null reference");
        }

        this.messages = messages;
        this.clients = clients;
    }

    public void createClient(SocketDescriptor socketDescriptor) throws IOException, ParserConfigurationException {
        if (null == socketDescriptor) {
            throw new NullPointerException("Null instead of socketDescriptor");
        }

        Client client = new Client(socketDescriptor.getSocket(), socketDescriptor.getProtocolName(), messages, clients);
        clients.add(client);
    }
}
