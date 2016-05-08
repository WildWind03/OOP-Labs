package ru.nsu.ccfit.chirikhin.chat.server;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Collection;
import java.util.LinkedList;

public class Server {
    private final Collection<Socket> sockets = new LinkedList<>();
    private final Collection<Thread> writeThreads = new LinkedList<>();
    private final Collection<Thread> readThreads = new LinkedList<>();

    private final static Logger logger = Logger.getLogger(Server.class.getName());

    private final LinkedList<PortListener> portListeners = new LinkedList<>();

    private final Thread portListenersThreads[];

    private final SocketController socketController;

    public Server(ServerConfig serverConfig) throws SocketException {
        if (null == serverConfig) {
            logger.error("Null pointer exception");
            throw new NullPointerException("Null pointer in constructor");
        }

        socketController = new SocketController();

        portListenersThreads = new Thread[serverConfig.getCountOfPorts()];

        serverConfig
                .stream()
                .forEach((socketListenerDescriptor) -> {
                    try {
                        portListeners.add(new PortListener(socketListenerDescriptor.getPort(), socketListenerDescriptor.getProtocol(), socketController));
                    } catch (IOException e) {
                        logger.error("Port num " + socketListenerDescriptor.getPort() + " was ignored because of IO exception");
                    }
                });





        /*if (portXML <= 0 || portSerialize <= 0) {
            logger.error("Invalid port value!");
            throw new IllegalArgumentException("Invalid port value");
        }

        try {
            serverSocketXML = new ServerSocket(portXML);
            serverSocketSerialize = new ServerSocket(portSerialize);
        } catch (IOException e) {
            logger.error("Error in opening socket");
            throw new SocketException("Error in opening socket");
        }

        serverSocketSerializeThread = new Thread();
        serverSocketXMLThread = new Thread(new ServerSocketXMLRunnable(serverSocketXML, ));
        */
    }

    public void start() throws IOException {

    }

}
