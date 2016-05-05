package ru.nsu.ccfit.chirikhin.chat.server;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collection;
import java.util.LinkedList;

public class Server {
    private final Collection<Socket> sockets = new LinkedList<>();
    private final Collection<Thread> writeThreads = new LinkedList<>();
    private final Collection<Thread> readThreads = new LinkedList<>();

    private final static Logger logger = Logger.getLogger(Server.class.getName());

    private final static int MAX_PORT_VALUE = 65535;

    private final ServerSocket serverSocket;

    public Server(int port) throws SocketNotOpenedException {
        if (port <= 0 || port > MAX_PORT_VALUE) {
            logger.error("Invalid port value!");
            throw new IllegalArgumentException("Port must be in range from 1 to 65535");
        }

        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            logger.error("Error in opening socket");
            throw new SocketNotOpenedException("Error in opening socket");
        }
    }

    public void start() throws IOException {
        while(true) {
            Socket newSocket = serverSocket.accept();
            Thread newWriteThread = new Thread();
            Thread newReadThread = new Thread();
        }
    }

}
