package ru.nsu.ccfit.chirikhin.chat.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class Server {
    private final Collection<Socket> sockets = new LinkedList<>();
    private final Collection<Thread> writeThreads = new LinkedList<>();
    private final Collection<Thread> readThreads = new LinkedList<>();

    private final static int MAX_PORT_VALUE = 65535;

    private final ServerSocket serverSocket;

    public Server(int port) {
        if (port <= 0 || port > MAX_PORT_VALUE) {
            throw new IllegalArgumentException("Port must be in range from 1 to 65535");
        }

        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            throw
        }
    }

    public void start() {

    }

}
