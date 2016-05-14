package ru.nsu.ccfit.chirikhin.chat.server;

import org.apache.log4j.Logger;
import ru.nsu.ccfit.chirikhin.chat.ProtocolName;

import java.net.Socket;

public class SocketDescriptor {
    private static final Logger logger = Logger.getLogger(SocketDescriptor.class.getName());
    private final ProtocolName protocolName;
    private final Socket socket;

    public ProtocolName getProtocolName() {
        return protocolName;
    }

    public Socket getSocket() {
        return socket;
    }

    public SocketDescriptor(ProtocolName protocolName, Socket socket) {
        if (null == protocolName || null == socket) {
            throw new NullPointerException("Null in constructor");
        }

        this.protocolName = protocolName;
        this.socket = socket;
    }
}
