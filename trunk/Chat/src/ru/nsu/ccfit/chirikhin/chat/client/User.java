package ru.nsu.ccfit.chirikhin.chat.client;

import org.apache.log4j.Logger;
import ru.nsu.ccfit.chirikhin.chat.*;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.Socket;
import java.util.Observer;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class User {
    private static final Logger logger = Logger.getLogger(User.class.getName());

    private final Thread readThread;
    private final Thread writeThread;

    private final BlockingQueue<ClientMessage> writeClientMessages = new LinkedBlockingQueue<>();

    private final SocketReader socketReader;

    private long sessionId;

    private final String username;

    private final ClientMessageController clientMessageController = new ClientMessageController(this);

    public User(ClientProperties clientProperties) throws ConnectionFailedException, IOException, ParserConfigurationException {

        Socket socket;
        logger.info("Connect");
        ConnectorToServer connectorToServer = new ConnectorToServer();

        socket = connectorToServer.connect(clientProperties.getPort(), clientProperties.getIp());
        logger.info("Connect success");

        username = clientProperties.getUsername();
        SocketWriter socketWriter = new SocketWriter(socket.getOutputStream(), ProtocolName.SERIALIZE, writeClientMessages);

        socketReader = new SocketReader(socket.getInputStream(), ProtocolName.SERIALIZE, clientMessageController);

        writeClientMessages.add(new LoginMessage(username, "Main client"));

        writeThread = new Thread(socketWriter);
        readThread = new Thread(socketReader);

        writeThread.start();
        readThread.start();
    }

    public void setSessionId(long sessionId) {
        this.sessionId = sessionId;
    }

    public void addEventObserver(Observer o) {
        clientMessageController.addObserver(o);
    }

    public void sendMessage(String message) {
        logger.info("Send message");
        writeClientMessages.add(new ClientTextMessage(message, username));
    }

    public void disconnect() {

        socketReader.stop();

        readThread.interrupt();
        writeThread.interrupt();

        try {
            readThread.join();
            writeThread.join();
        } catch (InterruptedException e) {
            logger.error("Interrupt");
        }
    }
}
