package ru.nsu.ccfit.chirikhin.chat.client;

import org.apache.log4j.Logger;

import java.net.Socket;
import java.util.Observable;
import java.util.Observer;
import java.util.Optional;

public class User implements Observer{
    private static final Logger logger = Logger.getLogger(User.class.getName());

    private Thread readThread;
    private Thread writeThread;
    private SocketReader socketReader;
    private SocketWriter socketWriter;

    private String username;

    public User() {

    }

    public void connect(ClientProperties clientProperties) throws ConnectionFailedException {
        Socket socket;
        logger.info("Connect");
        ConnectorToServer connectorToServer = new ConnectorToServer();

        socket = connectorToServer.connect(clientProperties.getPort(), clientProperties.getIp());
        logger.info("Connect success");

        username = clientProperties.getUsername();

        socketReader = new SocketReader();
        socketWriter = new SocketWriter();

        readThread = new Thread(socketReader);
        writeThread = new Thread(socketWriter);

        readThread.start();
        writeThread.start();
    }

    public void sendMessage(String message) {

    }


    @Override
    public void update(Observable o, Object arg) {

    }
}
