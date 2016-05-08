package ru.nsu.ccfit.chirikhin.chat.server;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.BlockingQueue;

public class ServerSocketXMLRunnable implements Runnable {
    private static final Logger logger = Logger.getLogger(ServerSocketXMLRunnable.class.getName());

    private final ServerSocket serverSocket;
    private final BlockingQueue<Socket> xmlSockets;

    public ServerSocketXMLRunnable(ServerSocket serverSocket, BlockingQueue<Socket> xmlSockets,
                                   BlockingQueue<Thread> xmlSocketReadThreads, BlockingQueue<Thread> xmlSocketWriteThreads) {
        this.serverSocket = serverSocket;
        this.xmlSockets = xmlSockets;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Socket newSocket = serverSocket.accept();
                xmlSockets.add(newSocket);
                Thread newSocketReadThread = new Thread();
                Thread newSocketWriteThread = new Thread();

                newSocketReadThread.start();
                newSocketWriteThread.start();

            } catch (IOException e) {
                logger.error("Socket IO error!");
            }
        }
    }
}
