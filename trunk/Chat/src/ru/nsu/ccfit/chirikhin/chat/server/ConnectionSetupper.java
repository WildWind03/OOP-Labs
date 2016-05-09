package ru.nsu.ccfit.chirikhin.chat.server;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;

public class ConnectionSetupper implements Runnable {
    private static final Logger logger = Logger.getLogger(ConnectionSetupper.class.getName());
    private final BlockingQueue<SocketDescriptor> socketDescriptors;
    private final BlockingQueue<Thread> socketReadersThreads;
    private final BlockingQueue<Thread> socketWritersThreads;
    private final BlockingQueue<UserMessageStore> userMessageStores;
    private final MessageController messageController;

    public ConnectionSetupper(BlockingQueue<SocketDescriptor> socketDescriptors, BlockingQueue<Thread> socketWritersThreads,
                              BlockingQueue<Thread> socketReadersThreads, BlockingQueue<UserMessageStore> userMessageStores, MessageController messageController) {
        this.socketDescriptors = socketDescriptors;
        this.socketReadersThreads = socketReadersThreads;
        this.socketWritersThreads = socketWritersThreads;
        this.messageController = messageController;
        this.userMessageStores = userMessageStores;
    }


    @Override
    public void run() {
        try {
            while (true) {
                SocketDescriptor socketDescriptor = socketDescriptors.take();

                UserMessageStore userMessageStore = new UserMessageStore();

                userMessageStores.add(userMessageStore);

                MessageSerializer messageSerializer = null;

                switch(socketDescriptor.getProtocolName()) {
                    case XML:
                        messageSerializer = new XMLSerializer();
                        break;
                    case SERIALIZE:
                        messageSerializer = new ObjectSerializer();
                        break;
                }
                SocketListener socketListener = new SocketListener(socketDescriptor.getSocket(), messageSerializer, messageController);
                SocketWriter socketWriter = new SocketWriter(userMessageStore, socketDescriptor.getSocket());
                Thread socketListenerThread = new Thread(socketListener);
                Thread socketWriterThread = new Thread(socketWriter);

                socketWritersThreads.add(socketWriterThread);
                socketReadersThreads.add(socketListenerThread);

                socketWriterThread.start();
                socketListenerThread.start();

            }
        } catch (InterruptedException e) {
            logger.error("Interrupt");
        } catch (IOException e) {
            logger.error("IOException");
        }
    }
}
