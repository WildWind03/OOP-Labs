package ru.nsu.ccfit.chirikhin.chat.client;

import org.apache.log4j.Logger;
import ru.nsu.ccfit.chirikhin.chat.*;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.Socket;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class User implements Observer {
    private static final Logger logger = Logger.getLogger(User.class.getName());

    private Thread readThread;
    private Thread writeThread;

    private SocketReader socketReader;
    private SocketWriter socketWriter;

    private final BlockingQueue<Message> readMessages = new LinkedBlockingQueue<>();
    private final BlockingQueue<Message> writeMessages = new LinkedBlockingQueue<>();

    private final ClientMessageController clientMessageController = new ClientMessageController(readMessages);
    private final Thread clientMessageControllerThread = new Thread(clientMessageController);

    private String username;

    public User() {

    }

    public void setOnNewMessageReceivedHandler(Observer o) {
        clientMessageController.addObserver(o);
    }

    public void connect(ClientProperties clientProperties) throws ConnectionFailedException, IOException, ParserConfigurationException {
        Socket socket;
        logger.info("Connect");
        ConnectorToServer connectorToServer = new ConnectorToServer();

        socket = connectorToServer.connect(clientProperties.getPort(), clientProperties.getIp());
        logger.info("Connect success");

        username = clientProperties.getUsername();

        socketWriter = new SocketWriter(socket, ProtocolName.SERIALIZE, writeMessages);
        socketReader = new SocketReader(socket, ProtocolName.SERIALIZE, readMessages);

        writeThread = new Thread(socketWriter);
        readThread = new Thread(socketReader);

        writeThread.start();
        readThread.start();

        clientMessageControllerThread.start();
    }

    public void sendMessage(String message) {
        logger.info("Send message");
        writeMessages.add(new ClientMessage(message, username));
    }

    public void disconnect() {
        readThread.interrupt();
        writeThread.interrupt();

        try {
            readThread.join();
            writeThread.join();
        } catch (InterruptedException e) {
            logger.error("Interrupt");
        }
    }


    @Override
    public void update(Observable o, Object arg) {

    }
}
