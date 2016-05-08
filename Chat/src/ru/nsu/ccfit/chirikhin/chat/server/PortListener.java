package ru.nsu.ccfit.chirikhin.chat.server;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class PortListener implements Runnable{
    private static final Logger logger = Logger.getLogger(PortListener.class.getName());
    private final int port;
    private final ProtocolName protocolName;

    private final SocketController socketController;

    private final ServerSocket serverSocket;

    public PortListener(int port, ProtocolName protocolName, SocketController socketController) throws IOException {
        if (port < 0) {
            logger.error("Port can not be negative");
            throw new IllegalArgumentException("Port can not be negative!");
        }

        if (null == protocolName) {
            logger.error("Protocol name is null");
            throw new NullPointerException("Protocol name is null");
        }

        if (null == socketController) {
            logger.error("SocketController is null reference");
            throw new NullPointerException("SocketController is null reference");
        }

        this.socketController = socketController;
        this.port = port;
        this.protocolName = protocolName;

        serverSocket = new ServerSocket(port);
    }

    @Override
    public void run() {
        try {
            while(true) {
                Socket newSocket = serverSocket.accept();
                socketController.initNewSocket(newSocket, protocolName);
            }
        } catch (IOException e) {
            logger.error("Interrupt");
        }
    }
}
