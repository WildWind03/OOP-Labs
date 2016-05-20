package ru.nsu.ccfit.chirikhin.chat.server;

import org.apache.log4j.Logger;
import ru.nsu.ccfit.chirikhin.chat.InputStreamReader;
import ru.nsu.ccfit.chirikhin.chat.LoginMessage;
import ru.nsu.ccfit.chirikhin.chat.Message;
import ru.nsu.ccfit.chirikhin.chat.OutputStreamWriter;
import ru.nsu.ccfit.chirikhin.chat.ProtocolName;
import ru.nsu.ccfit.chirikhin.chat.ServerMessage;
import ru.nsu.ccfit.chirikhin.chat.SignedLoginMessage;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Client {
    private static final Logger logger = Logger.getLogger(Client.class.getName());

    private final Thread writerThread;
    private final Thread readerThread;
    private final InputStreamReader inputStreamReader;

    private final long uniqueSessionId;

    private final BlockingQueue<Message> messagesForClient = new LinkedBlockingQueue<>();

    private String username = "NONAME";
    private String chatClientName = "UNKNOWN_CLIENT";
    private boolean isLoggedIn;


    public Client(Socket socket, ProtocolName protocolName, BlockingQueue<Message> messagesForServer, long uniqueSessionId) throws IOException, ParserConfigurationException {
        if (null == socket || null == protocolName || null == messagesForServer) {
            logger.error("Null reference in constructor");
            throw new NullPointerException("Null reference in constructor");
        }

        this.uniqueSessionId = uniqueSessionId;

        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(socket.getOutputStream(), protocolName, messagesForClient);

        inputStreamReader = new InputStreamReader(socket.getInputStream(), protocolName, message -> {
            if (message instanceof LoginMessage) {
                message = new SignedLoginMessage((LoginMessage) message, uniqueSessionId);
            }

            try {
                messagesForServer.put(message);
            } catch (InterruptedException e) {
                logger.error("Interrupt");
            }
        });

        writerThread = new Thread(outputStreamWriter, "Client Writer Thread");
        readerThread = new Thread(inputStreamReader, "Client Reader Thread");
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

        try {
            inputStreamReader.close();
        } catch (IOException e) {
            logger.error("Error while closing input stream reader");
        }

        writerThread.interrupt();
        readerThread.interrupt();

        writerThread.join();
        readerThread.join();

        logger.info("ClientView has been deleted!");
    }
}
