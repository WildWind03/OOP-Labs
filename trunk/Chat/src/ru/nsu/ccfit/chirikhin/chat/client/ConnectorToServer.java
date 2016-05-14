package ru.nsu.ccfit.chirikhin.chat.client;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ConnectorToServer {
    private static final Logger logger = Logger.getLogger(ConnectorToServer.class.getName());
    private final static int TIMEOUT = 3000;

    public Socket connect(int port, String ip) throws ConnectionFailedException {
        Socket socket = new Socket();
        try {
            socket.connect(new InetSocketAddress(ip, port), TIMEOUT);
        } catch (IOException e) {
            throw new ConnectionFailedException("Can't get connection");
        }

        return socket;
    }
}
