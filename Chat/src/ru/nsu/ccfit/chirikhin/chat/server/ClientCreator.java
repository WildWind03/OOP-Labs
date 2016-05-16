package ru.nsu.ccfit.chirikhin.chat.server;

import org.apache.log4j.Logger;
import ru.nsu.ccfit.chirikhin.chat.ServerMessage;
import ru.nsu.ccfit.chirikhin.cyclequeue.CycleQueue;
import ru.nsu.ccfit.chirikhin.chat.ClientMessage;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;

public class ClientCreator {
    private static final Logger logger = Logger.getLogger(ClientCreator.class.getName());

    private final BlockingQueue<Client> clients;

    private final CycleQueue<ServerMessage> messages;

    private final IdRegisterer idRegisterer = new IdRegisterer();

    public ClientCreator(BlockingQueue<Client> clients, CycleQueue<ServerMessage> messages) {
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

        Client client = new Client(socketDescriptor.getSocket(), socketDescriptor.getProtocolName(), messages, clients, idRegisterer.getNewId());
        clients.add(client);
    }
}
