package ru.nsu.ccfit.chirikhin.chat.server;

import org.apache.log4j.Logger;
import ru.nsu.ccfit.chirikhin.chat.service.Message;
import ru.nsu.ccfit.chirikhin.chat.service.ProtocolName;

import javax.xml.parsers.ParserConfigurationException;
import java.io.Closeable;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

public class PortListener implements Runnable, Closeable {
    private static final Logger logger = Logger.getLogger(PortListener.class.getName());

    private final ProtocolName protocolName;
    private final ServerSocket serverSocket;

    private final ConcurrentHashMap<String, Client> clients;

    private final IdRegisterer idRegisterer = IdRegistererSingleton.getInstance();

    private final BlockingQueue<Message> messages;

    public PortListener(int port, ProtocolName protocolName, ConcurrentHashMap<String, Client> clients, BlockingQueue<Message> messages) throws IOException {
        if (port < 0) {
            logger.error("Port can not be negative");
            throw new IllegalArgumentException("Port can not be negative!");
        }

        if (null == protocolName) {
            logger.error("Protocol name is null");
            throw new NullPointerException("Protocol name is null");
        }

        if (null == clients) {
            throw new NullPointerException("Null instead of clients");
        }

        if (null == messages) {
            throw new NullPointerException("Null reference instead of messages");
        }

        this.protocolName = protocolName;
        this.clients = clients;
        this.messages = messages;

        serverSocket = new ServerSocket(port);
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Socket newSocket = serverSocket.accept();
                String sessionId = idRegisterer.getNewId();
                try {
                    clients.put(sessionId, new Client(newSocket, protocolName, messages, sessionId));
                    logger.info("New client has been connected");
                } catch (IOException e) {
                    logger.error("Can not create a new client!");
                }
            }
        } catch (IOException e) {
            logger.error("Interrupt");
        } catch (ParserConfigurationException e) {
            logger.error("Error while parsing xml");
        }
    }

    @Override
    public void close() throws IOException {
        serverSocket.close();
    }
}
