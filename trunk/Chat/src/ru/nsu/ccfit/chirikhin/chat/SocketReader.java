package ru.nsu.ccfit.chirikhin.chat;

import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.Socket;

public class SocketReader implements Runnable {

    private final static Logger logger = Logger.getLogger(SocketReader.class.getName());

    private final MessageSerializer messageSerializer;
    private final MessageController messageController;

    public SocketReader(Socket socket, ProtocolName protocolName, MessageController messageController) throws IOException, ParserConfigurationException {
        if (null == socket || null == protocolName || null == messageController) {
            throw new NullPointerException("Null in constructor");
        }
        
        this.messageSerializer = MessageSerializerFactory.createSerializer(protocolName, socket.getInputStream());
        this.messageController = messageController;
    }

    @Override
    public void run() {
        try {
            while (true) {
                ClientMessage clientMessage = messageSerializer.read();
                logger.info("New message has been read");
                messageController.acceptMessage(clientMessage);
            }
        } catch (IOException | ClassNotFoundException | SAXException e) {
            logger.error("Error while reading message!");
        } catch (InterruptedException e) {
            logger.error("Interrupt");
        }
    }

    public void stop() {
        messageSerializer.stop();
    }

}
