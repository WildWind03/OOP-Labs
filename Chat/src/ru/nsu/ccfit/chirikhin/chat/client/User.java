package ru.nsu.ccfit.chirikhin.chat.client;

import org.apache.log4j.Logger;
import ru.nsu.ccfit.chirikhin.chat.*;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class User {
    private static final Logger logger = Logger.getLogger(User.class.getName());

    private final Thread readThread;
    private final Thread writeThread;

    private final BlockingQueue<Message> writeMessages = new LinkedBlockingQueue<>();

    private final SocketReader socketReader;

    private final String username;

    public User(ClientProperties clientProperties, ClientMessageController clientMessageController) throws ConnectionFailedException, IOException, ParserConfigurationException {
        Socket socket;
        logger.info("Connect");
        ConnectorToServer connectorToServer = new ConnectorToServer();

        socket = connectorToServer.connect(clientProperties.getPort(), clientProperties.getIp());
        logger.info("Connect success");

        username = clientProperties.getUsername();
        SocketWriter socketWriter = new SocketWriter(socket, ProtocolName.SERIALIZE, writeMessages);

        socketReader = new SocketReader(socket, ProtocolName.SERIALIZE, clientMessageController);

        writeMessages.add(new LoginMessage(username, ""));

        writeThread = new Thread(socketWriter);
        readThread = new Thread(socketReader);

        writeThread.start();
        readThread.start();
    }

    public void sendMessage(String message) {
        logger.info("Send message");
        writeMessages.add(new ClientMessage(message, username));
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
