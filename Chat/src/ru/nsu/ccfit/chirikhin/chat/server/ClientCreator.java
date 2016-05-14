package ru.nsu.ccfit.chirikhin.chat.server;

import org.apache.log4j.Logger;
import ru.nsu.ccfit.chirikhin.chat.Message;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;

public class ClientCreator implements Runnable {
    private static final Logger logger = Logger.getLogger(ClientCreator.class.getName());

    private final BlockingQueue<SocketDescriptor> socketDescriptors;
    private final BlockingQueue<Message> messages;
    private final BlockingQueue<Client> clients;

    public ClientCreator(BlockingQueue<SocketDescriptor> socketDescriptors, BlockingQueue<Message> messages, BlockingQueue<Client> clients) {
        if (null == socketDescriptors || null == messages || null == clients) {
            throw new NullPointerException("Null reference");
        }

        this.socketDescriptors = socketDescriptors;
        this.clients = clients;
        this.messages = messages;
    }

    @Override
    public void run() {
        try {
            while (true) {
                SocketDescriptor socketDescriptor = socketDescriptors.take();
                Client client = new Client(socketDescriptor.getSocket(), socketDescriptor.getProtocolName(), messages);
                clients.add(client);
            }
        } catch (InterruptedException e) {
            logger.error("Interrupt");
        } catch (IOException e) {
            logger.error("IOException");
        } catch (ParserConfigurationException e) {
            logger.error("Error while parsing");
        }
    }
}
