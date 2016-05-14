package ru.nsu.ccfit.chirikhin.chat.server;

import org.apache.log4j.Logger;
import ru.nsu.ccfit.chirikhin.chat.Message;
import ru.nsu.ccfit.chirikhin.chat.SocketReader;
import ru.nsu.ccfit.chirikhin.chat.SocketWriter;

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
        if (null == socket || null == protocolName || null == messages) {
            logger.error("Null reference in constructor");
            throw new NullPointerException("Null reference in constructor");
        }

        socketReader = new SocketReader(socket, protocolName, messages);
        socketWriter = new SocketWriter(socket, protocolName, clientMessages);

        writerThread = new Thread(socketWriter);
        readerThread = new Thread(socketReader);

        writerThread.start();
        readerThread.start();

        logger.info("New client has been connected");
    }

    public void receiveMessage(Message message) {
        if (null == message) {
            throw new NullPointerException("Message can not be null");
        }

        clientMessages.add(message);
    }

    public void delete() throws InterruptedException {

        writerThread.interrupt();
        readerThread.interrupt();

        writerThread.join();
        readerThread.join();

        logger.info("ClientView has been deleted!");
    }
}
