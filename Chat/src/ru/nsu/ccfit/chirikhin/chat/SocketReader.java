package ru.nsu.ccfit.chirikhin.chat;

import jdk.nashorn.internal.ir.Block;
import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.BlockingQueue;

public class SocketReader implements Runnable {

    private final static Logger logger = Logger.getLogger(SocketReader.class.getName());

    private final MessageSerializer messageSerializer;
    private final BlockingQueue<? extends Message> messages;

    public SocketReader(InputStream inputStream, ProtocolName protocolName, BlockingQueue<? extends Message> messages) throws IOException, ParserConfigurationException {
        if (null == inputStream || null == protocolName || null == messages) {
            throw new NullPointerException("Null in constructor");
        }

        this.messageSerializer = MessageSerializerFactory.createSerializer(protocolName, inputStream);
        this.messageController = messageController;
    }

    @Override
    public void run() {
        try {
            while (true) {
                logger.info("Read messsage");
                Message message = messageSerializer.read();
                logger.info("New message has been read");
                messageController.acceptMessage(message);
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
