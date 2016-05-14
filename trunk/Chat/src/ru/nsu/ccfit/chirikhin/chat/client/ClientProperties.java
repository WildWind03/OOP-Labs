package ru.nsu.ccfit.chirikhin.chat.client;

import org.apache.log4j.Logger;

public class ClientProperties {
    private final int port;
    private final String ip;
    private final String username;

    public ClientProperties(int port, String ip, String username) {
        if (port < 0) {
            throw new IllegalArgumentException("Port must be negative");
        }

        if (null == ip || null == username) {
            throw new NullPointerException("Null reference");
        }

        this.port = port;
        this.ip = ip;
        this.username = username;
    }

    public int getPort() {
        return port;
    }

    public String getIp() {
        return ip;
    }

    public String getUsername() {
        return username;
    }

    private static final Logger logger = Logger.getLogger(ClientProperties.class.getName());
}
