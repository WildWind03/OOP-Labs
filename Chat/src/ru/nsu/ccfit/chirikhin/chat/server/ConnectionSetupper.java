package ru.nsu.ccfit.chirikhin.chat.server;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;

public class ConnectionSetupper implements Runnable {
    private static final Logger logger = Logger.getLogger(ConnectionSetupper.class.getName());
    private final BlockingQueue<SocketDescriptor> socketDescriptors;
    private final BlockingQueue<Thread> socketReadersThreads;
    private final BlockingQueue<Thread> socketWritersThreads;
    private final BlockingQueue<Message> messages;

    public ConnectionSetupper(BlockingQueue<SocketDescriptor> socketDescriptors, BlockingQueue<Thread> socketWritersThreads,
                              BlockingQueue<Thread> socketReadersThreads, BlockingQueue<Message> messages) {
        this.socketDescriptors = socketDescriptors;
        this.socketReadersThreads = socketReadersThreads;
        this.socketWritersThreads = socketWritersThreads;
        this.messages = messages;
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
                SocketReader socketReader = new SocketReader(socketDescriptor.getSocket(), messageSerializer, messageController);
                SocketWriter socketWriter = new SocketWriter(userMessageStore, socketDescriptor.getSocket());
                Thread socketListenerThread = new Thread(socketReader);
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
