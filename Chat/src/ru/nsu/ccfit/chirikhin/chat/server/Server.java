package ru.nsu.ccfit.chirikhin.chat.server;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.SocketException;
import java.util.LinkedList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Server {

    private final static Logger logger = Logger.getLogger(Server.class.getName());

    private final LinkedList <PortListener> portListeners = new LinkedList<>();
    private final LinkedList <Thread> portListenersThreads = new LinkedList<>();

    private final Thread connectionSetupperThread;
    private final ConnectionSetupper connectionSetupper;

    private final BlockingQueue<Message> messages = new LinkedBlockingQueue<>();

    //private final BlockingQueue<UserMessageStore> userMessageStores = new LinkedBlockingQueue<>();

    //private final BlockingQueue<Thread> socketWritersThreads = new LinkedBlockingQueue<>();
    //private final BlockingQueue<Thread> socketListenersThreads = new LinkedBlockingQueue<>();

    private final MessageController messageController = new MessageController(messages, userMessageStores);
    private final Thread messageControllerThread = new Thread(messageController);

    private final BlockingQueue<SocketDescriptor> socketDescriptors = new LinkedBlockingQueue<>();

    public Server(ServerConfig serverConfig) throws SocketException {
        if (null == serverConfig) {
            logger.error("Null pointer exception");
            throw new NullPointerException("Null pointer in constructor");
        }



        connectionSetupper = new ConnectionSetupper(socketDescriptors, socketListenersThreads, socketWritersThreads, messages);
        connectionSetupperThread = new Thread(connectionSetupper);
        connectionSetupperThread.start();

        serverConfig
                .stream()
                .forEach((socketListenerDescriptor) -> {
                    try {
                        PortListener portListener = new PortListener(socketListenerDescriptor.getPort(), socketListenerDescriptor.getProtocol(), socketDescriptors);
                        portListeners.add(portListener);

                        Thread newPortListenerThread = new Thread(portListener);
                        portListenersThreads.add(newPortListenerThread);


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
        portListenersThreads.forEach(Thread::interrupt);
        messageControllerThread.interrupt();

        messageControllerThread.join();
        for (Thread thread : portListenersThreads) {
            thread.join();
        }
    }

}
