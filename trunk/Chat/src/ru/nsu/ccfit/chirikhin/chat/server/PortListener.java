package ru.nsu.ccfit.chirikhin.chat.server;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

public class PortListener implements Runnable{
    private static final Logger logger = Logger.getLogger(PortListener.class.getName());
    private final ProtocolName protocolName;

    private final ServerSocket serverSocket;

    private final BlockingQueue<SocketDescriptor> socketDescriptors;

    public PortListener(int port, ProtocolName protocolName, BlockingQueue<SocketDescriptor> socketDescriptors) throws IOException {
        if (port < 0) {
            logger.error("Port can not be negative");
            throw new IllegalArgumentException("Port can not be negative!");
        }

        if (null == protocolName) {
            logger.error("Protocol name is null");
            throw new NullPointerException("Protocol name is null");
        }

        if (null == socketDescriptors) {
            throw new NullPointerException("Null instead of socketDescriptors");
        }

        this.socketDescriptors = socketDescriptors;
        this.protocolName = protocolName;

        serverSocket = new ServerSocket(port);
    }

    @Override
    public void run() {
        try {
            while(true) {
                Socket newSocket = serverSocket.accept();
                socketDescriptors.add(new SocketDescriptor(protocolName, newSocket));
            }
        } catch (IOException e) {
            logger.error("Interrupt");
        }
    }

    public void closeConnection() throws IOException {
        serverSocket.close();
    }
}
