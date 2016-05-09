package ru.nsu.ccfit.chirikhin.chat.server;

import org.apache.log4j.Logger;

import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class SocketController {
    private static final Logger logger = Logger.getLogger(SocketController.class.getName());
    private final BlockingQueue<User> users = new LinkedBlockingQueue<>();

    public SocketController() {

    }

    public void initNewSocket(Socket socket, ProtocolName protocolName) {
        users.add(new User(protocolName, socket));
    }
}
