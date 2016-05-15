package ru.nsu.ccfit.chirikhin.chat.server;

import org.apache.log4j.Logger;
import ru.nsu.ccfit.chirikhin.autoqueue.Autoqueue;
import ru.nsu.ccfit.chirikhin.chat.Message;
import ru.nsu.ccfit.chirikhin.chat.MessageController;
import ru.nsu.ccfit.chirikhin.chat.ProtocolName;
import ru.nsu.ccfit.chirikhin.chat.SocketWriter;
import ru.nsu.ccfit.chirikhin.chat.SocketReader;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Client {
    private static final Logger logger = Logger.getLogger(Client.class.getName());

    private final Thread writerThread;
    private final Thread readerThread;

    private String username;
    private boolean isRegistered;

    private final SocketReader socketReader;

    private final BlockingQueue<Message> clientMessages = new LinkedBlockingQueue<>();

    private final MessageController messageController;

    public Client(Socket socket, ProtocolName protocolName, Autoqueue<Message> messages, BlockingQueue<Client> clients) throws IOException, ParserConfigurationException {
        if (null == socket || null == protocolName || null == messages || null == clients) {
            logger.error("Null reference in constructor");
            throw new NullPointerException("Null reference in constructor");
        }

        this.messageController = new ServerMessageController(messages, clients, this);

        SocketWriter socketWriter = new SocketWriter(socket, protocolName, clientMessages);;
        socketReader = new SocketReader(socket, protocolName, messageController);

        writerThread = new Thread(socketWriter);
        writerThread.setName("Client Writer Thread");

        readerThread = new Thread(socketReader);
        readerThread.setName("Client Reader Thread");

        writerThread.start();
        readerThread.start();

        logger.info("New client has been connected");
    }

    public void setUsermame(String newUsername) {
        this.username = username;
    }

    public void receiveMessage(Message message) {
        if (null == message) {
            throw new NullPointerException("Message can not be null");
        }

        clientMessages.add(message);
    }

    public void delete() throws InterruptedException {

        socketReader.stop();

        writerThread.interrupt();
        readerThread.interrupt();

        writerThread.join();
        readerThread.join();

        logger.info("ClientView has been deleted!");
    }
}
