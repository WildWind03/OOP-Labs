package ru.nsu.ccfit.chirikhin.chat.client.model;

import org.apache.log4j.Logger;
import ru.nsu.ccfit.chirikhin.chat.service.ClientMessageEnum;
import ru.nsu.ccfit.chirikhin.chat.service.CommandLogin;
import ru.nsu.ccfit.chirikhin.chat.service.CommandLogout;
import ru.nsu.ccfit.chirikhin.chat.service.Message;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.Socket;
import java.util.Observer;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Client {
    private static final int TIMEOUT_FOR_READING_FROM_SOCKET = 3000;
    private static final int TIMEOUT_FOR_WAIT_ANSWER_IN_CONNECT = 4000;
    private final static String CHAT_CLIENT_NAME = "Windogram";

    private final Object lock = new Object();

    private String sessionId;
    private LoginState loginState = LoginState.NO_ANSWER;

    private static final Logger logger = Logger.getLogger(Client.class.getName());

    private final Thread readThread;
    private final Thread writeThread;
    private final BlockingQueue<Message> messagesFromServer = new LinkedBlockingQueue<>();
    private final BlockingQueue<Message> messagesForServer = new LinkedBlockingQueue<>();
    private final BlockingQueue<ClientMessageEnum> historyOfCommands = new LinkedBlockingQueue<>();
    private final ClientInputStreamReader inputStreamReader;
    private final ClientOutputStreamWriter clientOutputStreamWriter;


    private final ClientMessageController clientMessageController = new ClientMessageController(historyOfCommands, messagesFromServer, this);
    private final Thread clientMessageControllerThread = new Thread(clientMessageController, "Client Message Controller Thread");

    public Client(ClientProperties clientProperties) throws ConnectionFailedException, IOException, ParserConfigurationException {
        if (null == clientProperties) {
            throw new NullPointerException("ClientProperties is null");
        }

        ConnectorToServer connectorToServer = new ConnectorToServer();

        Socket socket = connectorToServer.connect(clientProperties.getPort(), clientProperties.getIp(), TIMEOUT_FOR_WAIT_ANSWER_IN_CONNECT);
        socket.setSoTimeout(TIMEOUT_FOR_READING_FROM_SOCKET);

        clientOutputStreamWriter = new ClientOutputStreamWriter(socket.getOutputStream(), clientProperties.getProtocolName(), messagesForServer);
        inputStreamReader = new ClientInputStreamReader(socket.getInputStream(), clientProperties.getProtocolName(), messagesFromServer);

        writeThread = new Thread(clientOutputStreamWriter, "User Writer Thread");
        readThread = new Thread(inputStreamReader, "User Reader Thread");

        writeThread.start();
        readThread.start();
        clientMessageControllerThread.start();
    }

    public LoginState login(String nickname) {
        if (null == nickname) {
            throw new NullPointerException("Null reference instead of nickname");
        }

        loginState = LoginState.NO_ANSWER;

        sendMessage(new CommandLogin(nickname, CHAT_CLIENT_NAME));

        synchronized (lock) {
            try {
                lock.wait(TIMEOUT_FOR_WAIT_ANSWER_IN_CONNECT);
            } catch (InterruptedException e) {
                logger.error("Interrupt");
            }
        }

        return loginState;

    }

    public LoginState getLoginState() {
        return loginState;
    }

    public void setSessionId(String sessionId) {
        if (null == sessionId) {
            throw new NullPointerException("Session id can not be null");
        }

        this.sessionId = sessionId;
    }

    public void setLoginState(LoginState newState) {
        if (null == newState) {
            throw new NullPointerException("Null reference instead of newState");
        }

        synchronized (lock) {
            loginState = newState;
            lock.notifyAll();
        }
    }

    public String getSessionId(){
        return sessionId;
    }

    public void addMessageControllerObserver(Observer o) {
        if (null == o) {
            throw new NullPointerException("Observer is null");
        }

        clientMessageController.addObserver(o);
    }

    public void sendMessage(Message message) {
        if (null == message) {
            throw new NullPointerException("Message is null while sending message");
        }

        try {
            historyOfCommands.put(MessageTypeConverter.getMessageType(message));
            messagesForServer.put(message);
        } catch (InterruptedException e) {
            logger.error("Interrupt while sending message");
        }
    }

    public void onStop()  {
        CommandLogout commandLogout = new CommandLogout(sessionId);
        sendMessage(commandLogout);
    }

    public void disconnect() {

        try {
            inputStreamReader.close();
            clientOutputStreamWriter.close();
        } catch (IOException e) {
            logger.error("Error while closing Input Stream Reader");
        }

        readThread.interrupt();
        writeThread.interrupt();

        try {
            readThread.join();
            writeThread.join();
        } catch (InterruptedException e) {
            logger.error("Interrupt while joining threads");
        }

        clientMessageControllerThread.interrupt();
    }
}
