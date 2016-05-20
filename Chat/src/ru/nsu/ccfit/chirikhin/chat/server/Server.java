package ru.nsu.ccfit.chirikhin.chat.server;

import org.apache.log4j.Logger;
import ru.nsu.ccfit.chirikhin.chat.Message;
import ru.nsu.ccfit.chirikhin.chat.ServerMessageController;
import ru.nsu.ccfit.chirikhin.cyclequeue.CycleQueue;

import java.io.IOException;
import java.net.SocketException;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

public class Server {

    private final static Logger logger = Logger.getLogger(Server.class.getName());
    private final static int COUNT_OF_MESSAGES_TO_SEND_WHEN_AUTHORIZE = 10;

    private final LinkedList<PortListener> portListeners = new LinkedList<>();
    private final LinkedList<Thread> portListenersThreads = new LinkedList<>();

    private final CycleQueue<Message> messagesForNewClients = new CycleQueue<>(COUNT_OF_MESSAGES_TO_SEND_WHEN_AUTHORIZE);
    private final ConcurrentHashMap<Long, Client> clients = new ConcurrentHashMap<>();
    private final BlockingQueue<Message> messagesFromClients = new LinkedBlockingQueue<>();

    private final ServerMessageController messageController = new ServerMessageController(messagesForNewClients, clients, messagesFromClients);
    private final Thread messageControllerThread = new Thread(messageController, "Message Controller Thread");

    public Server(ServerConfig serverConfig) throws SocketException {
        if (null == serverConfig) {
            logger.error("Null pointer exception");
            throw new NullPointerException("Null pointer in constructor");
        }

        logger.info("Server created");

        serverConfig
                .stream()
                .forEach((socketListenerDescriptor) -> {
                    try {
                        PortListener portListener = new PortListener(socketListenerDescriptor.getPort(), socketListenerDescriptor.getProtocol(), clients, messagesFromClients);
                        portListeners.add(portListener);

                        Thread newPortListenerThread = new Thread(portListener, "Port " + socketListenerDescriptor.getPort() + " Listener Thread");
                        portListenersThreads.add(newPortListenerThread);

                    } catch (IOException e) {
                        logger.error("Port num " + socketListenerDescriptor.getPort() + " was ignored because of IO exception");
                    }
                });
    }

    public void start() throws IOException {
        portListenersThreads.forEach(Thread::start);
        messageControllerThread.start();

        logger.info("Server started");
    }

    public void stop() throws InterruptedException {
        for (PortListener portListener : portListeners) {
            try {
                portListener.close();
            } catch (IOException e) {
                logger.error("IO exception while closing port listener");
            }
        }

        messageControllerThread.interrupt();
        messageControllerThread.join();

        for (Thread thread : portListenersThreads) {
            thread.interrupt();
            thread.join();
        }

        for (Map.Entry<Long, Client> client : clients.entrySet()) {
            client.getValue().delete();
        }
    }

}
