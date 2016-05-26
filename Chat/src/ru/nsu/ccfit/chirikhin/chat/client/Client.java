package ru.nsu.ccfit.chirikhin.chat.client;

import org.apache.log4j.Logger;
import ru.nsu.ccfit.chirikhin.chat.*;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.Socket;
import java.util.Observer;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Client {
    private static final int TIMEOUT_FOR_READING_FROM_SOCKET = 3000;
    private static final int TIMEOUT_FOR_WAIT_ANSWER_IN_CONNECT = 400;
    private final static String CHAT_CLIENT_NAME = "Windogram";

    private long sessionId;
    private String username;

    private static final Logger logger = Logger.getLogger(Client.class.getName());

    private final Thread readThread;
    private final Thread writeThread;
    private final BlockingQueue<Message> messagesFromServer = new LinkedBlockingQueue<>();
    private final BlockingQueue<Message> messagesForServer = new LinkedBlockingQueue<>();
    private final BlockingQueue<ClientMessageEnum> historyOfCommands = new LinkedBlockingQueue<>();
    private final InputStreamReader inputStreamReader;
    private final OutputStreamWriter outputStreamWriter;


    private final ClientMessageController clientMessageController = new ClientMessageController(historyOfCommands, messagesFromServer, this);
    private final Thread clientMessageControllerThread = new Thread(clientMessageController, "Client Message Controller Thread");

    public Client(ClientProperties clientProperties) throws ConnectionFailedException, IOException, ParserConfigurationException {
        ConnectorToServer connectorToServer = new ConnectorToServer();

        Socket socket = connectorToServer.connect(clientProperties.getPort(), clientProperties.getIp(), TIMEOUT_FOR_WAIT_ANSWER_IN_CONNECT);
        socket.setSoTimeout(TIMEOUT_FOR_READING_FROM_SOCKET);

        outputStreamWriter = new OutputStreamWriter(socket.getOutputStream(), clientProperties.getProtocolName(),
                messagesForServer);
        inputStreamReader = new InputStreamReader(socket.getInputStream(), clientProperties.getProtocolName(), message -> {
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

        readThread = new Thread(inputStreamReader, "User Reader Thread");
        writeThread = new Thread(outputStreamWriter, "User Writer Thread");

        readThread.start();
        writeThread.start();
        clientMessageControllerThread.start();
    }

    public void login(String nickname) {
        setNickname(nickname);
        sendMessage(new LoginMessage(nickname, CHAT_CLIENT_NAME));
    }

    public void setNickname(String nickname) {
        this.username = nickname;
    }

    public void setSessionId(long sessionId) {
        this.sessionId = sessionId;
    }

    public long getSessionId(){
        return sessionId;
    }

    public void addMessageControllerObserver(Observer o) {
        clientMessageController.addObserver(o);
    }

    public String getNickname() {
        return username;
    }

    public void sendMessage(Message message) {
        try {
            historyOfCommands.put(MessageTypeConverter.getMessageType(message));
            messagesForServer.put(message);
        } catch (InterruptedException e) {
            logger.error("Interrupt while sending message");
        }
    }

    public void onStop()  {
        ClientLogoutClientMessage clientLogoutClientMessage = new ClientLogoutClientMessage(sessionId);
        sendMessage(clientLogoutClientMessage);
    }

    public void disconnect() {

        try {
            inputStreamReader.close();
        } catch (IOException e) {
            logger.error("Error while closing Input Stream Reader");
        }

        readThread.interrupt();
        clientMessageControllerThread.interrupt();

        try {
            clientMessageControllerThread.join();
            readThread.join();
        } catch (InterruptedException e) {
            logger.error("Interrupt");
        }
    }
}
