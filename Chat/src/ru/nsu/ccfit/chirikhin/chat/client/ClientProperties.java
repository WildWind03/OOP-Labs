package ru.nsu.ccfit.chirikhin.chat.client;

import org.apache.log4j.Logger;
import ru.nsu.ccfit.chirikhin.chat.ProtocolName;

public class ClientProperties {
    private static final Logger logger = Logger.getLogger(ClientProperties.class.getName());
    private final int port;
    private final String ip;
    private final ProtocolName protocolName;
    private final String nickname;

    public ClientProperties(int port, String ip, ProtocolName protocolName, String nickname) {
        if (port < 0) {
            throw new IllegalArgumentException("Port must be negative");
        }

        if (null == ip || null == protocolName || null == nickname) {
            throw new NullPointerException("Null reference");
        }

        this.port = port;
        this.ip = ip;
        this.protocolName = protocolName;
        this.nickname = nickname;
    }

    public int getPort() {
        return port;
    }

    public String getIp() {
        return ip;
    }

    public ProtocolName getProtocolName() {
        return protocolName;
    }

    public String getNickname() {
        return nickname;
    }
}
