package ru.nsu.ccfit.chirikhin.chat.server;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Server {
    //private final Collection<Thread> writeThreads = new LinkedList<>();
    //private final Collection<Thread> readThreads = new LinkedList<>();

    private final static Logger logger = Logger.getLogger(Server.class.getName());

    private final LinkedList <PortListener> portListeners = new LinkedList<>();
    private final LinkedList <Thread> portListenersThreads = new LinkedList<>();

    private final SocketController socketController;

    public Server(ServerConfig serverConfig) throws SocketException {
        if (null == serverConfig) {
            logger.error("Null pointer exception");
            throw new NullPointerException("Null pointer in constructor");
        }

        socketController = new SocketController();

        serverConfig
                .stream()
                .forEach((socketListenerDescriptor) -> {
                    try {
                        PortListener portListener = new PortListener(socketListenerDescriptor.getPort(), socketListenerDescriptor.getProtocol(), socketController);
                        portListeners.add(portListener);
                        Thread newPortListenerThread = new Thread(portListener);
                        portListenersThreads.add(newPortListenerThread);
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
       portListenersThreads.forEach(Thread::start);
    }

}
