package ru.nsu.ccfit.chirikhin.chat.server;

import org.apache.log4j.Logger;

import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class User {
    private static final Logger logger = Logger.getLogger(User.class.getName());
    private final BlockingQueue<Message> messages = new LinkedBlockingQueue<>();
    /*private final ProtocolName protocolName;
    private final Socket socket;
    private final Thread socketWriterThread;
    private final Thread socketListenerThread;
    private final SocketListener socketListener;
    private final SocketWriter socketWriter;
    */

    public User(ProtocolName protocolName, Socket socket) {
        /*this.protocolName = protocolName;
        this.socket = socket;

        MessageTransformer messageTransformer;

        switch(protocolName) {
            case
        }
        this.socketWriter = new SocketWriter(messages, socket, )
        */
    }
}
