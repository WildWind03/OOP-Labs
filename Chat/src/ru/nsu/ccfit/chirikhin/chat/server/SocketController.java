package ru.nsu.ccfit.chirikhin.chat.server;

import org.apache.log4j.Logger;

import java.net.Socket;
import java.util.concurrent.BlockingQueue;

public class SocketController {
    private static final Logger logger = Logger.getLogger(SocketController.class.getName());
    private final BlockingQueue<Socket> sockets;

    public SocketController(BlockingQueue<Socket> sockets) {
        this.sockets = sockets;
    }

    public void initNewSocket(Socket socket, ProtocolName protocolName) {

    }
}
