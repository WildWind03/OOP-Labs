package ru.nsu.ccfit.chirikhin.chat.server;

import org.apache.log4j.Logger;

public class SocketListenerDescriptor {
    private static final Logger logger = Logger.getLogger(SocketListenerDescriptor.class.getName());
    private final int port;
    private final ProtocolName protocolName;

    public SocketListenerDescriptor(int port, ProtocolName protocolName) {
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
