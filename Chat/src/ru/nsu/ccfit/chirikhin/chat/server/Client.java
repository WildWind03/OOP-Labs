package ru.nsu.ccfit.chirikhin.chat.server;

import org.apache.log4j.Logger;
import ru.nsu.ccfit.chirikhin.chat.MessageController;
import ru.nsu.ccfit.chirikhin.chat.ProtocolName;
import ru.nsu.ccfit.chirikhin.chat.ServerMessage;
import ru.nsu.ccfit.chirikhin.chat.SocketReader;
import ru.nsu.ccfit.chirikhin.chat.SocketWriter;
import ru.nsu.ccfit.chirikhin.cyclequeue.CycleQueue;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Client {
    private static final Logger logger = Logger.getLogger(Client.class.getName());

    private final Thread writerThread;
    private final Thread readerThread;
    private final long uniqueSessionId;
    private final SocketReader socketReader;
    private final BlockingQueue<ServerMessage> clientClientMessages = new LinkedBlockingQueue<>();
    private final MessageController messageController;
    private String username = "NONAME";
    private String chatClientName = "UNKNOWN_CLIENT";
    private boolean isRegistered;

    public Client(Socket socket, ProtocolName protocolName, CycleQueue<ServerMessage> messages, BlockingQueue<Client> clients, long uniqueSessionId) throws IOException, ParserConfigurationException {
        if (null == socket || null == protocolName || null == messages || null == clients) {
            logger.error("Null reference in constructor");
            throw new NullPointerException("Null reference in constructor");
        }

        this.uniqueSessionId = uniqueSessionId;

        this.messageController = new ServerMessageController(messages, clients, this);

        SocketWriter socketWriter = new SocketWriter(socket.getOutputStream(), protocolName, clientClientMessages);
        socketReader = new SocketReader(socket.getInputStream(), protocolName, messageController);

        writerThread = new Thread(socketWriter);
        writerThread.setName("Client Writer Thread");

        readerThread = new Thread(socketReader);
        readerThread.setName("Client Reader Thread");

        writerThread.start();
        readerThread.start();

        logger.info("New client has been connected");
    }

    public String getUsername() {
        return username;
    }

    public void setUsermame(String username) {
        this.username = username;
    }

    public void setChatClientName(String chatClientName) {
        this.chatClientName = chatClientName;
    }

    public void register() {
        isRegistered = true;
    }

    public void receiveMessage(ServerMessage clientMessage) {
        if (null == clientMessage) {
            throw new NullPointerException("Message can not be null");
        }

        clientClientMessages.add(clientMessage);
    }

    public boolean isRegistered() {
        return isRegistered;
    }

    public long getId() {
        return uniqueSessionId;
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
