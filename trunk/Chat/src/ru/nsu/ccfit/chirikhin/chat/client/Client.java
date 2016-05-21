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
    private final int TIMEOUT_FOR_WAIT_ANSWER = 400;
    private final static String CHAT_CLIENT_NAME = "Windogram";

    private final Object lock = new Object();

    private boolean isNotified;
    private long sessionId;
    private String username;

    private static final Logger logger = Logger.getLogger(Client.class.getName());

    private final Thread readThread;
    private final MessageSender messageSender;
    private final BlockingQueue<Message> messagesFromServer = new LinkedBlockingQueue<>();
    private final InputStreamReader inputStreamReader;


    private final ClientMessageController clientMessageController = new ClientMessageController(messagesFromServer, this);
    private final Thread clientMessageControllerThread = new Thread(clientMessageController, "Client Message Controller Thread");

    public Client(ClientProperties clientProperties) throws ConnectionFailedException, IOException, ParserConfigurationException {
        Socket socket;
        ConnectorToServer connectorToServer = new ConnectorToServer();

        socket = connectorToServer.connect(clientProperties.getPort(), clientProperties.getIp());
        socket.setSoTimeout(TIMEOUT_FOR_READING_FROM_SOCKET);

        messageSender = MessageSenderFactory.createMessageSender(clientProperties.getProtocolName(), socket.getOutputStream());

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

        readThread = new Thread(inputStreamReader, "User Reader Thread");

        readThread.start();
        clientMessageControllerThread.start();
    }

    public void login(String nickname) throws TimeoutException {
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

    public void sendMessage(Message message) throws TimeoutException {
        messageSender.send(message);

        synchronized (lock) {
            try {
                clientMessageController.setPreviousMessage(MessageTypeConverter.getMessageType(message));
                isNotified = false;
                lock.wait(TIMEOUT_FOR_WAIT_ANSWER);
            } catch (InterruptedException e) {
                logger.error("Interrupt");
            }

            if (!isNotified) {
                throw new TimeoutException("There is no answer from server");
            }
        }
    }

    public void wakeUp() {
        synchronized (lock) {
            isNotified = true;
            lock.notifyAll();
        }
    }

    public void onStop() throws TimeoutException {
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
