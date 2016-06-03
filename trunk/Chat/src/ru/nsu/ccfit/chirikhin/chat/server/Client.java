package ru.nsu.ccfit.chirikhin.chat.server;

import org.apache.log4j.Logger;
import ru.nsu.ccfit.chirikhin.chat.service.*;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Client {
    private static final Logger logger = Logger.getLogger(Client.class.getName());
    private static final int TIMEOUT_FOR_READING_FROM_SOCKET = 3000;

    private final Thread writerThread;
    private final Thread readerThread;
    private final ServerInputStreamReader inputStreamReader;
    private final ServerOutputStreamWriter serverOutputStreamWriter;

    private final String uniqueSessionId;

    private final BlockingQueue<Message> messagesForClient = new LinkedBlockingQueue<>();

    private String username = "Rhinoceros";
    private String chatClientName = "Magic valley";
    private boolean isLoggedIn = false;
    private boolean isFinished = false;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Client client = (Client) o;

        if (uniqueSessionId != null ? !uniqueSessionId.equals(client.uniqueSessionId) : client.uniqueSessionId != null)
            return false;
        return username != null ? username.equals(client.username) : client.username == null;

    }

    @Override
    public int hashCode() {
        int result = uniqueSessionId != null ? uniqueSessionId.hashCode() : 0;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        return result;
    }

    public Client(Socket socket, ProtocolName protocolName, BlockingQueue<Message> messagesForServer, String uniqueSessionId) throws IOException, ParserConfigurationException {
        if (null == socket || null == protocolName || null == messagesForServer || null == uniqueSessionId) {
            logger.error("Null reference in constructor");
            throw new NullPointerException("Null reference in constructor");
        }

        this.uniqueSessionId = uniqueSessionId;

        socket.setSoTimeout(TIMEOUT_FOR_READING_FROM_SOCKET);

        serverOutputStreamWriter = new ServerOutputStreamWriter(socket.getOutputStream(), protocolName, messagesForClient);
        inputStreamReader = new ServerInputStreamReader(socket.getInputStream(), protocolName, () -> {
            try {
                if (!isFinished) {
                    messagesForServer.put(new CommandUnexpectedLogout(uniqueSessionId));
                }
            } catch (InterruptedException e) {
                logger.error("Interrupt");
            }
        }, message -> {
            if (message instanceof CommandLogin) {
                message = new CommandSignedLogin((CommandLogin)message, uniqueSessionId);
            }

            messagesForServer.put(message);
        });

        writerThread = new Thread(serverOutputStreamWriter, "Client Writer Thread");
        readerThread = new Thread(inputStreamReader, "Client Reader Thread");
        writerThread.start();
        readerThread.start();

        logger.info("New client has been created");
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

        logger.info("User " + getUsername() + " received new message");

        try {
            messagesForClient.put(clientMessage);
        } catch (InterruptedException e) {
            logger.error("Interrupt");
        }
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public String getChatClientName() {
        return chatClientName;
    }

    public void delete() {
        try {
            inputStreamReader.close();
        } catch (IOException e) {
            logger.error("Error while closing input stream reader");
        }

        serverOutputStreamWriter.close();
        readerThread.interrupt();
        writerThread.interrupt();

        try {
            readerThread.join();
            writerThread.join();
        } catch (InterruptedException e) {
            logger.error("Interrupt");
        }

        logger.info("ClientView has been deleted!");
    }
}
