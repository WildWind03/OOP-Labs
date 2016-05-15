package ru.nsu.ccfit.chirikhin.chat.server;

import org.apache.log4j.Logger;
import ru.nsu.ccfit.chirikhin.chat.Message;

import java.io.IOException;
import java.net.SocketException;
import java.util.LinkedList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Server {

    private final static Logger logger = Logger.getLogger(Server.class.getName());

    private final LinkedList <PortListener> portListeners = new LinkedList<>();
    private final LinkedList <Thread> portListenersThreads = new LinkedList<>();

    private final BlockingQueue<Message> messages = new LinkedBlockingQueue<>();
    private final BlockingQueue<Client> clients = new LinkedBlockingQueue<>();
    private final BlockingQueue<SocketDescriptor> socketDescriptors = new LinkedBlockingQueue<>();

    private final ServerMessageController serverMessageController = new ServerMessageController(messages, clients);
    private final Thread messageControllerThread = new Thread(serverMessageController);

    private final ClientCreator clientCreator = new ClientCreator(socketDescriptors, messages, clients);
    private final Thread clientCreatorThread = new Thread(clientCreator);

    public Server(ServerConfig serverConfig) throws SocketException {
        if (null == serverConfig) {
            logger.error("Null pointer exception");
            throw new NullPointerException("Null pointer in constructor");
        }

        serverConfig
                .stream()
                .forEach((socketListenerDescriptor) -> {
                    try {
                        PortListener portListener = new PortListener(socketListenerDescriptor.getPort(), socketListenerDescriptor.getProtocol(), socketDescriptors);
                        portListeners.add(portListener);

                        Thread newPortListenerThread = new Thread(portListener);
                        portListenersThreads.add(newPortListenerThread);
                        newPortListenerThread.setName("Port Listener Thread");


                    } catch (IOException e) {
                        logger.error("Port num " + socketListenerDescriptor.getPort() + " was ignored because of IO exception");
                    }
                });
    }

    public void start() throws IOException {
        portListenersThreads.forEach(Thread::start);
        messageControllerThread.start();
        clientCreatorThread.start();
    }

    public void stop() throws InterruptedException {
        for (PortListener portListener : portListeners) {
            try {
                portListener.closeConnection();
            } catch (IOException e) {
                logger.error("IO exception while closing port listener");
            }
        }
        messageControllerThread.interrupt();
        clientCreatorThread.interrupt();

        clientCreatorThread.join();
        messageControllerThread.join();
        for (Thread thread : portListenersThreads) {
            thread.join();
        }
    }

}
