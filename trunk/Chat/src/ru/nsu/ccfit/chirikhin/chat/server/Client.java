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
    private final InputStreamReader inputStreamReader;
    private final OutputStreamWriter outputStreamWriter;

    private final long uniqueSessionId;

    private final BlockingQueue<Message> messagesForClient = new LinkedBlockingQueue<>();

    private String username = "NONAME";
    private boolean isExit = false;
    private String chatClientName = "UNKNOWN_CLIENT";
    private boolean isLoggedIn = false;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Client client = (Client) o;

        return uniqueSessionId == client.uniqueSessionId;

    }

    @Override
    public int hashCode() {
        return (int) (uniqueSessionId ^ (uniqueSessionId >>> 32));
    }

    public Client(Socket socket, ProtocolName protocolName, BlockingQueue<Message> messagesForServer, long uniqueSessionId) throws IOException, ParserConfigurationException {
        if (null == socket || null == protocolName || null == messagesForServer) {
            logger.error("Null reference in constructor");
            throw new NullPointerException("Null reference in constructor");
        }

        this.uniqueSessionId = uniqueSessionId;

        socket.setSoTimeout(TIMEOUT_FOR_READING_FROM_SOCKET);

        outputStreamWriter = new OutputStreamWriter(socket.getOutputStream(), protocolName, messagesForClient);

        inputStreamReader = new InputStreamReader(socket.getInputStream(), protocolName, message -> {
            if (message instanceof CommandLogin) {
                message = new CommandSignedLogin((CommandLogin) message, uniqueSessionId);
            }

            try {
                messagesForServer.put(message);
            } catch (InterruptedException e) {
                logger.error("Interrupt");
            }
        }, () -> {
            try {
                if (!isExit()) {
                    messagesForServer.put(new CommandUnexpectedLogout(uniqueSessionId));
                }
            } catch (InterruptedException e) {
                logger.error("Interrupt");
            }
        });

        writerThread = new Thread(outputStreamWriter, "Client Writer Thread");
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

    public boolean isExit() {
        return isExit;
    }

    public void finishAndStop() {
        readerThread.interrupt();
        try {
            inputStreamReader.close();
            readerThread.join();
        } catch (InterruptedException e) {
            logger.error("Interrupt");
        } catch (IOException e) {
            logger.error("IO while closing");
        }

        isExit = true;
        outputStreamWriter.finishAndStop();
    }

    public void delete() {

        if (!isExit) {
            readerThread.interrupt();
            writerThread.interrupt();
        }

        try {
            if (!isExit) {
                inputStreamReader.close();
                outputStreamWriter.close();
            }
        } catch (IOException e) {
            logger.error("Error while closing input stream reader");
        }

        try {
            if (!isExit) {
                readerThread.join();
                writerThread.join();
            }
        } catch (InterruptedException e) {
            logger.error("Interrupt");
        }

        logger.info("ClientView has been deleted!");
    }
}
