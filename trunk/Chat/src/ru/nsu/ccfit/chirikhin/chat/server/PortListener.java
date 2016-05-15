package ru.nsu.ccfit.chirikhin.chat.server;

import org.apache.log4j.Logger;
import ru.nsu.ccfit.chirikhin.chat.ProtocolName;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class PortListener implements Runnable {
    private static final Logger logger = Logger.getLogger(PortListener.class.getName());

    private final ProtocolName protocolName;
    private final ServerSocket serverSocket;
    private final ClientCreator clientCreator;

    public PortListener(int port, ProtocolName protocolName, ClientCreator clientCreator) throws IOException {
        if (port < 0) {
            logger.error("Port can not be negative");
            throw new IllegalArgumentException("Port can not be negative!");
        }

        if (null == protocolName) {
            logger.error("Protocol name is null");
            throw new NullPointerException("Protocol name is null");
        }

        if (null == clientCreator) {
            throw new NullPointerException("Null instead of client creator");
        }

        this.clientCreator = clientCreator;
        this.protocolName = protocolName;

        serverSocket = new ServerSocket(port);
    }

    @Override
    public void run() {
        try {
            while(true) {
                Socket newSocket = serverSocket.accept();
                clientCreator.createClient(new SocketDescriptor(protocolName, newSocket));
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
