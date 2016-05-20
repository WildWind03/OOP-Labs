package ru.nsu.ccfit.chirikhin.chat.server;

import org.apache.log4j.Logger;
import ru.nsu.ccfit.chirikhin.chat.ClientMessage;
import ru.nsu.ccfit.chirikhin.chat.ProtocolName;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

public class PortListener implements Runnable {
    private static final Logger logger = Logger.getLogger(PortListener.class.getName());

    private final ProtocolName protocolName;
    private final ServerSocket serverSocket;
    private final ConcurrentHashMap<Long, Client> clients;
    private final IdRegisterer idRegisterer = IdRegistererSingleton.getInstance();
    private final BlockingQueue<ClientMessage> messages;

    public PortListener(int port, ProtocolName protocolName, ConcurrentHashMap<Long, Client> clients, BlockingQueue<ClientMessage> messages) throws IOException {
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
            throw new NullPointerException("Null reference isntead of messages");
        }

        this.protocolName = protocolName;
        this.clients = clients;
        this.messages = messages;

        serverSocket = new ServerSocket(port);
    }

    @Override
    public void run() {
        try {
            while (true) {
                Socket newSocket = serverSocket.accept();
                long sessionId = idRegisterer.getNewId();
                clients.put(sessionId, new Client(newSocket, protocolName, messages, sessionId));
            }
        } catch (IOException e) {
            logger.error("Interrupt");
        } catch (ParserConfigurationException e) {
            logger.error("Error while parsing xml");
        }
    }

    public void closeConnection() throws IOException {
        serverSocket.close();
    }
}
