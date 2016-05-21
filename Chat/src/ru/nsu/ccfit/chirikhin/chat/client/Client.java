package ru.nsu.ccfit.chirikhin.chat.client;

import org.apache.log4j.Logger;
import ru.nsu.ccfit.chirikhin.chat.ClientLogoutClientMessage;
import ru.nsu.ccfit.chirikhin.chat.ClientMessageController;
import ru.nsu.ccfit.chirikhin.chat.ClientTextMessage;
import ru.nsu.ccfit.chirikhin.chat.ConnectionFailedMessage;
import ru.nsu.ccfit.chirikhin.chat.InputStreamReader;
import ru.nsu.ccfit.chirikhin.chat.LoginMessage;
import ru.nsu.ccfit.chirikhin.chat.Message;
import ru.nsu.ccfit.chirikhin.chat.OutputStreamWriter;
import ru.nsu.ccfit.chirikhin.chat.ProtocolName;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.Socket;
import java.util.Observer;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Client {
    private static final int TIMEOUT_FOR_READING_FROM_SOCKET = 3000;

    private static final Logger logger = Logger.getLogger(Client.class.getName());

    private final Thread readThread;
    private final Thread writeThread;

    private final BlockingQueue<Message> messagesForServer = new LinkedBlockingQueue<>();
    private final BlockingQueue<Message> messagesFromServer = new LinkedBlockingQueue<>();

    private final InputStreamReader inputStreamReader;

    private long sessionId;

    private final String username;

    private final ClientMessageController clientMessageController = new ClientMessageController(messagesFromServer, this);
    private final Thread clientMessageControllerThread = new Thread(clientMessageController, "Client Message Controller Thread");

    public Client(ClientProperties clientProperties) throws ConnectionFailedException, IOException, ParserConfigurationException {

        Socket socket;
        logger.info("Connect");
        ConnectorToServer connectorToServer = new ConnectorToServer();

        socket = connectorToServer.connect(clientProperties.getPort(), clientProperties.getIp());
        socket.setSoTimeout(TIMEOUT_FOR_READING_FROM_SOCKET);
        logger.info("Connect success");

        username = clientProperties.getUsername();
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(socket.getOutputStream(), ProtocolName.SERIALIZE, messagesForServer);

        inputStreamReader = new InputStreamReader(socket.getInputStream(), ProtocolName.SERIALIZE, message -> {
            try {
                messagesFromServer.put(message);
            } catch (InterruptedException e) {
                logger.error("Interrupt");
            }
        }, () -> {
            try {
                messagesFromServer.put(new ConnectionFailedMessage());
            } catch (InterruptedException e) {
                logger.error("Interrupt");
            }
        });

        messagesForServer.add(new LoginMessage(username, "Windagram"));

        writeThread = new Thread(outputStreamWriter, "User Writer Thread");
        readThread = new Thread(inputStreamReader, "User Reader Thread");

        writeThread.start();
        readThread.start();
        clientMessageControllerThread.start();
    }

    public void setSessionId(long sessionId) {
        this.sessionId = sessionId;
    }

    public void addMessageControllerObserver(Observer o) {
        clientMessageController.addObserver(o);
    }

    public void sendMessage(String message) {
        logger.info("Send message");
        messagesForServer.add(new ClientTextMessage(message, username, sessionId));
    }

    public void onStop() {
        ClientLogoutClientMessage clientLogoutClientMessage = new ClientLogoutClientMessage(sessionId);
        messagesForServer.add(clientLogoutClientMessage);
    }

    public void disconnect() {

        try {
            inputStreamReader.close();
        } catch (IOException e) {
            logger.error("Error while closing Input Stream Reader");
        }

        readThread.interrupt();
        writeThread.interrupt();
        clientMessageControllerThread.interrupt();

        try {
            clientMessageControllerThread.join();
            readThread.join();
            writeThread.join();
        } catch (InterruptedException e) {
            logger.error("Interrupt");
        }
    }
}
