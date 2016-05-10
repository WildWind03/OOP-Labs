package ru.nsu.ccfit.chirikhin.chat.server;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.Socket;
import java.util.LinkedList;

public class Client {
    private static final Logger logger = Logger.getLogger(Client.class.getName());
    private final Thread writerThread;
    private final Thread readerThread;

    private final SocketWriter socketWriter;
    private final SocketReader socketReader;

    private final LinkedList<Message> messages;

    public Client(Socket socket, MessageSerializer messageSerializer) throws IOException {
        socketReader = new SocketReader(socket, messageSerializer);
        writerThread = new Thread
    }
}
