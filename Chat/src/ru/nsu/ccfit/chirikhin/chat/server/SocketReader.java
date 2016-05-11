package ru.nsu.ccfit.chirikhin.chat.server;

import org.apache.log4j.Logger;
import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

public class SocketReader implements Runnable {

    private final static Logger logger = Logger.getLogger(SocketReader.class.getName());

    private final MessageSerializer messageSerializer;
    private final BlockingQueue<Message> messages;

    public SocketReader(Socket socket, ProtocolName protocolName, BlockingQueue<Message> messages) throws IOException, ParserConfigurationException {
        this.messageSerializer = MessageSerializerFactory.createSerializer(protocolName, socket.getInputStream());
        this.messages = messages;
    }

    @Override
    public void run() {
        while(true) {
            try {
                Message message = messageSerializer.read();
                messages.add(message);
            } catch (IOException | ClassNotFoundException | SAXException e) {
                logger.error("Error while reading message!");
            }
        }
    }
}
