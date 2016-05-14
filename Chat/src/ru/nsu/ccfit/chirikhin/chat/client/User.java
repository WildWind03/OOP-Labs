package ru.nsu.ccfit.chirikhin.chat.client;

import org.apache.log4j.Logger;
import ru.nsu.ccfit.chirikhin.chat.Message;
import ru.nsu.ccfit.chirikhin.chat.SocketReader;
import ru.nsu.ccfit.chirikhin.chat.SocketWriter;
import ru.nsu.ccfit.chirikhin.chat.server.ProtocolName;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.Socket;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class User implements Observer{
    private static final Logger logger = Logger.getLogger(User.class.getName());

    private Thread readThread;
    private Thread writeThread;
    private SocketReader socketReader;
    private SocketWriter socketWriter;

    private final BlockingQueue<Message> readMessages = new LinkedBlockingQueue<>();
    private final BlockingQueue<Message> writeMessages = new LinkedBlockingQueue<>();

    private String username;

    public User() {

    }

    public void connect(ClientProperties clientProperties) throws ConnectionFailedException, IOException, ParserConfigurationException {
        Socket socket;
        logger.info("Connect");
        ConnectorToServer connectorToServer = new ConnectorToServer();

        socket = connectorToServer.connect(clientProperties.getPort(), clientProperties.getIp());
        logger.info("Connect success");

        username = clientProperties.getUsername();

        logger.info("Before Socket Reader");
        socketReader = new SocketReader(socket, ProtocolName.SERIALIZE, readMessages);
        //socketWriter = new SocketWriter(socket, ProtocolName.SERIALIZE, writeMessages);

        //readThread = new Thread(socketReader);
        //writeThread = new Thread(socketWriter);

        //readThread.start();
        //writeThread.start();
    }

    public void sendMessage(String message) {

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
