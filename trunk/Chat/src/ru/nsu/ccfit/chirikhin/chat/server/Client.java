package ru.nsu.ccfit.chirikhin.chat.server;

import org.apache.log4j.Logger;
import ru.nsu.ccfit.chirikhin.chat.*;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Client {
    private static final Logger logger = Logger.getLogger(Client.class.getName());

    private final Thread writerThread;
    private final Thread readerThread;
    private final SocketReader socketReader;

    private final long uniqueSessionId;

    private final BlockingQueue<ServerMessage> messagesForClient = new LinkedBlockingQueue<>();

    private String username = "NONAME";
    private String chatClientName = "UNKNOWN_CLIENT";
    private boolean isLoggedIn;


    public Client(Socket socket, ProtocolName protocolName, BlockingQueue<ClientMessage> messagesForServer, long uniqueSessionId) throws IOException, ParserConfigurationException {
        if (null == socket || null == protocolName || null == messagesForServer) {
            logger.error("Null reference in constructor");
            throw new NullPointerException("Null reference in constructor");
        }

        this.uniqueSessionId = uniqueSessionId;

        SocketWriter socketWriter = new SocketWriter(socket.getOutputStream(), protocolName, messagesForClient);
        socketReader = new SocketReader(socket.getInputStream(), protocolName, messagesForServer);

        writerThread = new Thread(socketWriter, "Client Writer Thread");
        readerThread = new Thread(socketReader, "Client Reader Thread");
        writerThread.start();
        readerThread.start();

        logger.info("New client has been connected");
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setChatClientName(String chatClientName) {
        this.chatClientName = chatClientName;
    }

    public void login() {
        isLoggedIn = true;
    }

    public void receiveMessage(ServerMessage clientMessage) {
        if (null == clientMessage) {
            throw new NullPointerException("Message can not be null");
        }

        messagesForClient.add(clientMessage);
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public long getUniqueSessionId() {
        return uniqueSessionId;
    }

    public String getChatClientName() {
        return chatClientName;
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
