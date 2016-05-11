package ru.nsu.ccfit.chirikhin.chat.server;

import org.apache.log4j.Logger;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Client {
    private static final Logger logger = Logger.getLogger(Client.class.getName());

    private final Thread writerThread;
    private final Thread readerThread;

    private final SocketWriter socketWriter;
    private final SocketReader socketReader;

    private final BlockingQueue<Message> clientMessages = new LinkedBlockingQueue<>();

    public Client(Socket socket, ProtocolName protocolName, BlockingQueue<Message> messages) throws IOException, ParserConfigurationException {
        socketReader = new SocketReader(socket, protocolName, messages);
        socketWriter = new SocketWriter(socket, protocolName, clientMessages);

        writerThread = new Thread(socketWriter);
        readerThread = new Thread(socketReader);

        writerThread.start();
        readerThread.start();
    }
}
