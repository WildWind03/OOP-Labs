package ru.nsu.ccfit.chirikhin.chat.client.model;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ConnectorToServer {
    private static final Logger logger = Logger.getLogger(ConnectorToServer.class.getName());

    public Socket connect(int port, String ip, int timeout) throws ConnectionFailedException {
        if (port < 0 || timeout < 0 || null == ip) {
            throw new IllegalArgumentException("Illegal args");
        }

        Socket socket = new Socket();
        try {
            socket.connect(new InetSocketAddress(ip, port), timeout);
        } catch (IOException e) {
            throw new ConnectionFailedException("Can't get connection");
        }

        return socket;
    }
}
