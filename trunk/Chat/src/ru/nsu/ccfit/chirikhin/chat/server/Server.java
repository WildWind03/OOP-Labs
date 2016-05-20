package ru.nsu.ccfit.chirikhin.chat.server;

import org.apache.log4j.Logger;
import ru.nsu.ccfit.chirikhin.chat.ClientMessage;
import ru.nsu.ccfit.chirikhin.chat.ServerMessage;
import ru.nsu.ccfit.chirikhin.cyclequeue.CycleQueue;

import java.io.IOException;
import java.net.SocketException;
import java.util.LinkedList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

public class Server {

    private final static Logger logger = Logger.getLogger(Server.class.getName());
    private final static int COUNT_OF_MESSAGES_TO_SEND_WHEN_AUTORIZE = 10;

    private final LinkedList<PortListener> portListeners = new LinkedList<>();
    private final LinkedList<Thread> portListenersThreads = new LinkedList<>();

    private final CycleQueue<ServerMessage> messagesForNewCleints = new CycleQueue<>(COUNT_OF_MESSAGES_TO_SEND_WHEN_AUTORIZE);
    private final ConcurrentHashMap<Long, Client> clients = new ConcurrentHashMap<>();

    private final ClientCreator clientCreator = new ClientCreator(clients, messagesForNewCleints);

    private final BlockingQueue<ClientMessage> messagesFromClients = new LinkedBlockingQueue<>();

    private final ServerMessageController messageController = new ServerMessageController(messagesForNewCleints, clients, messagesFromClients);
    private final Thread messageControllerThread = new Thread(messageController, "Message Controller Thread");

    public Server(ServerConfig serverConfig) throws SocketException {
        if (null == serverConfig) {
            logger.error("Null pointer exception");
            throw new NullPointerException("Null pointer in constructor");
        }

        serverConfig
                .stream()
                .forEach((socketListenerDescriptor) -> {
                    try {
                        PortListener portListener = new PortListener(socketListenerDescriptor.getPort(), socketListenerDescriptor.getProtocol(), clientCreator);
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
        messageControllerThread.join();

        for (Thread thread : portListenersThreads) {
            thread.interrupt();
            thread.join();
        }

        for (Client client : clients) {
            client.delete();
        }
    }

}
