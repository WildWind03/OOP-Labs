package ru.nsu.ccfit.chirikhin.chat.client;

import org.apache.log4j.Logger;
import ru.nsu.ccfit.chirikhin.chat.ProtocolName;

public class ClientProperties {
    private static final Logger logger = Logger.getLogger(ClientProperties.class.getName());
    private final int port;
    private final String ip;
    private final String username;
    private final ProtocolName protocolName;

    public ClientProperties(int port, String ip, String username, ProtocolName protocolName) {
        if (port < 0) {
            throw new IllegalArgumentException("Port must be negative");
        }

        if (null == ip || null == username || null == protocolName) {
            throw new NullPointerException("Null reference");
        }

        this.port = port;
        this.ip = ip;
        this.username = username;
        this.protocolName = protocolName;
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

    public ProtocolName getProtocolName() {
        return protocolName;
    }
}
