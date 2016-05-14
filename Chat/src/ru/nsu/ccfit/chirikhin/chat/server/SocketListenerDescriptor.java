package ru.nsu.ccfit.chirikhin.chat.server;

import org.apache.log4j.Logger;
import ru.nsu.ccfit.chirikhin.chat.ProtocolName;

public class SocketListenerDescriptor {
    private static final Logger logger = Logger.getLogger(SocketListenerDescriptor.class.getName());
    private final int port;
    private final ProtocolName protocolName;

    public SocketListenerDescriptor(int port, ProtocolName protocolName) {
        if (port < 0) {
            throw new IllegalArgumentException("Port can not be negative");
        }

        if (null == protocolName) {
            throw new NullPointerException("Null instead of protocol name");
        }

        this.port = port;
        this.protocolName = protocolName;
    }

    public int getPort() {
        return port;
    }

    public ProtocolName getProtocol() {
        return protocolName;
    }
}
